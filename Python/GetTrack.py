from PIL import Image, ImageDraw, ImageFont
import sys, os, requests, json
from Config import Config

d = os.path.dirname(__file__)

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

id = sys.argv[1]
search = sys.argv[2]

# Load Data
j = json.loads(requests.get('http://ws.audioscrobbler.com/2.0/?method=track.search&track={}&api_key={}&format=json'.format(search, Config['LastFM']['Key'])).content)

try:
    artist  = j['results']['trackmatches']['track'][0]['artist']
    song    = j['results']['trackmatches']['track'][0]['name']
except:
    artist  = '(Unknown!)'
    song    = search

f = open(ff('assets/%s_track.jpg' % id), 'wb')
if(os.path.isfile(ff('assets/artists/%s.jpg' % artist))):
    f.write(open(ff('assets/artists/%s.jpg' % artist), 'rb').read())
else:
    f.write(open(ff('assets/MusicPic.jpg'), 'rb').read())
f.close()


img = Image.new('RGBA', (500, 500), (255, 255, 255))
art = Image.open(ff('assets/%s_track.jpg' % id)).resize((500, 500)).convert('RGBA')

for i in range(0, 100):
    bg = Image.new('RGBA', (500, 1), (0, 0, 0, int(i * 200 / 100)))
    art.alpha_composite(bg, (0, 400 + i))

draw = ImageDraw.Draw(art)
font1 = ImageFont.truetype(ff("assets/comic.ttf"), 24)
font2 = ImageFont.truetype(ff("assets/comic.ttf"), 18)
draw.text((20, 420), artist, (255, 255, 255), font=font1)
draw.text((20, 460), song, (255, 255, 255), font=font2)

img.alpha_composite(art, (0, 0))

user = Image.open(ff('assets/%s.jpg' % id)).resize((70, 70)).convert('RGBA')
user = add_corners(user, 35)

img.alpha_composite(user, (415, 415))

out = Image.new('RGB', (500, 500), (52, 73, 94))
out.paste(img, mask=img.split()[3])
out.save(ff('assets/%s_track.jpg' % id))
