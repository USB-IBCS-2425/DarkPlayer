from graphics import*

class Cell():

    def __init__(self, win, coord, color):
        self.win = win
        self.coord = coord
        self.minX = coord[0]*(win.getWidth()/8) - 300*coord[0]/8 + 50
        self.minY = coord[1]*(win.getHeight()/8) - 100*coord[1]/8 + 50
        self.maxX = self.minX + (win.getWidth() - 300)/8
        self.maxY = self.minY + (win.getHeight() - 100)/8
        self.r = Rectangle(Point(self.minX, self.minY), Point(self.maxX, self.maxY))
        self.r.setFill(color)
        self.r.setOutline("black")
        self.r.draw(win)

        self.circ = Circle(Point((self.maxX + self.minX)/2, (self.maxY + self.minY)/2), 40)

        self.piece = ""
        self.highlighted = False

    def isClicked(self, p):
        x = p.getX()
        y = p.getY()
        if x > self.minX:
            if x < self.maxX:
                if y > self.minY:
                    if y < self.maxY:
                        return True
        return False

    def highlight(self):
        self.r.setOutline("blue")
        self.r.setWidth(4)
        self.highlighted = True

    def unHighlight(self):
        self.r.setOutline("black")
        self.r.setWidth(1)
        self.highlighted = False

    def updatePiece(self, piece):
        self.piece = piece
        if self.piece == "black":   
            self.circ.setFill("black")
        else:
            self.circ.setFill("white")
        self.circ.undraw()
        self.circ.draw(self.win)
        
    def tryUpdatePiece(self, piece):
        self.piece = piece
        

    def getCoord(self):
        return self.coord

    def hMove(self):
        self.r.setOutline("pink")
        self.r.setWidth(4)

    def unHMove(self):
        self.r.setOutline("black")
        self.r.setWidth(1)

    def empty(self):
        self.piece = ""
        self.circ.undraw()
    def emptyPiece(self):
        self.piece = "";

            
            
        
