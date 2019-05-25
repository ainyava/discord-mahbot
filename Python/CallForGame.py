from telegram import Bot
from telegram.ext import Updater
from PIL import Image, ImageDraw, ImageFont
import sys, os, json
from Config import Config
import requests

d = os.path.dirname(__file__)


TELEGRAM_TOKEN = 'YOUR_TELEGRAM_BOT_TOKEN'
CHAT_ID = 'YOUR_TELEGRAM_CHAT_ID'

# Path function
def ff(name):
    return os.path.join(d, name)


def add_corners(im, rad):
    circle = Image.new('L', (rad * 2, rad * 2), 0)
    draw = ImageDraw.Draw(circle)
    draw.ellipse((0, 0, rad * 2, rad * 2), fill=255)
    alpha = Image.new('L', im.size, 255)
    w, h = im.size
    alpha.paste(circle.crop((0, 0, rad, rad)), (0, 0))
    alpha.paste(circle.crop((0, rad, rad, rad * 2)), (0, h - rad))
    alpha.paste(circle.crop((rad, 0, rad * 2, rad)), (w - rad, 0))
    alpha.paste(circle.crop((rad, rad, rad * 2, rad * 2)), (w - rad, h - rad))
    im.putalpha(alpha)
    return im


reqid = sys.argv[1]
reqname = sys.argv[2]
game = sys.argv[3]
players = [{
    'id': reqid,
    'name': reqname
}]
i = 4
while (i < len(sys.argv)):
    players.append({
        "id": sys.argv[i],
        "name": sys.argv[i + 1]
    })
    i += 2

ft1 = ImageFont.truetype(ff("assets/comic.ttf"), 24)
ft2 = ImageFont.truetype(ff("assets/comic.ttf"), 22)
ft3 = ImageFont.truetype(ff("assets/comic.ttf"), 18)

bg = Image.open(ff('assets/%s_1.jpg' % game)).convert('RGBA')
draw = ImageDraw.Draw(bg)
pos = 10
for player in players:
    avatar = Image.open(ff('assets/%s.jpg' % player['id'])).resize((70, 70)).convert('RGBA')
    avatar = add_corners(avatar, 35)
    bg.alpha_composite(avatar, (10, pos))
    draw.text((90, pos + 20), player['name'], (255, 255, 255), font=ft3)
    pos += 80

bg = bg.convert('RGB')
bg.save(ff('assets/%s_GameSend.jpg' % reqid))

try:
    game = Config['Games'][game]
except:
    pass


api = 'https://api.telegram.org/bot%s/' % token

req = requests.post(api+"sendPhoto",
                    files={
                        'photo': open(ff('assets/%s_GameSend.jpg') % reqid, 'rb')
                    },
                    data={
                        'chat_id': "%s" % CHAT_ID,
                        'caption': '%s need you to play %s' % (reqname, game)
                    },
                    # If telegram is banned in your country uncomment this line and configure your proxy :)
					#proxies=dict(http='socks5://127.0.0.1:9050', https='socks5://127.0.0.1:9050'))
j = json.loads(req.content)
requests.get(api+'pinChatMessage?chat_id='+CHAT_ID+'&message_id='+str(j['result']['message_id']), proxies=dict(http='socks5://127.0.0.1:9050', https='socks5://127.0.0.1:9050'))