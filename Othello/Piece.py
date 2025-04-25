from graphics import*

class Piece():

    def __init__(self, win, coord, img):
        self.x = coord[0]*(win.getWidth()/8) + win.getWidth()/16
        self.y = coord[1]*(win.getHeight()/8) + win.getHeight()/16
        self.im = Image(Point(self.x, self.y), img)
        self.im.draw(win)
        
