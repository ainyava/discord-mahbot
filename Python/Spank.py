from PIL import Image
import sys, os

d = os.path.dirname(__file__)


# Path function
def ff(name):
    return os.path.join(d, name)

id = sys.argv[1]

img = Image.open(ff('assets/spank.jpg'))
spanker = Image.open(ff('assets/%s.jpg' % sys.argv[2])).resize((120, 120))
spanked = Image.open(ff('assets/%s.jpg' % sys.argv[3])).resize((120, 120))
img.paste(spanker, (270, 0))
img.paste(spanked, (450, 110))
img.save(ff('assets/%s_spank.jpg' % id))