from graphics import*

class Button():

    def __init__(self, win, text, size, pos):

        self.r = Rectangle(Point(pos.getX() - size, pos.getY() - size/2),
                           Point(pos.getX() + size, pos.getY() + size/2))
        self.r.draw(win)
        self.t = Text(pos, text)
        self.t.draw(win)

        self.minX = pos.getX() - size
        self.maxX = pos.getX() + size
        self.minY = pos.getY() - size
        self.maxY = pos.getY() + size

    def isClicked(self, p):
        x = p.getX()
        y = p.getY()
        if x > self.minX:
            if x < self.maxX:
                if y > self.minY:
                    if y < self.maxY:
                        return True

        return False





        
