from graphics import*
from Button import*
import math
import random

def loadData(win, label):
    if label:
        f = open("gatorDataLabeled.txt")
    else:
        f = open("gatorDataUnlabeled.txt")
    allData = f.read()
    listData = allData.split()
    gators = []
    crocs = []
    
    if label:
        for i in range(0, len(listData), 3):
            x = float(listData[i])
            y = float(listData[i+1])
            lab = listData[i+2]
            
            p = Point(int(200 + x*100), int(500 - y*1.75))
            r = Rectangle(Point(p.getX() - 2, p.getY() - 2), Point(p.getX() + 2, p.getY() + 2))
            
            if lab == "alligator":
                color = "red"
                r.setFill(color)
                r.draw(win)
                gators.append(r)
            else:
                color = "blue"
                r.setFill(color)
                r.draw(win)
                crocs.append(r)
    else:
        for i in range(0, len(listData), 2):
            x = float(listData[i])
            y = float(listData[i+1])
            p = Point(int(200 + x*100), int(500 - y*1.75))
            r = Rectangle(Point(p.getX() - 2, p.getY() - 2), Point(p.getX() + 2, p.getY() + 2))
            
            ran = random.random()
            if ran > 0.5:
                color = "red"
                r.setFill(color)
                r.draw(win)
                gators.append(r)
            else:
                color = "blue"
                r.setFill(color)
                r.draw(win)
                crocs.append(r)

    return gators, crocs

def initCentroid(win, g, c):
    c1 = Point(200 + 2.12*100, int(500 - 200*1.75))
    r1 = Rectangle(Point(c1.getX() - 4, c1.getY() - 4), Point(c1.getX() + 4, c1.getY() + 4))
    r1.setFill("darkred")
    r1.draw(win)
    c2 = Point(200 + 3*100, int(500 - 300*1.75))
    r2 = Rectangle(Point(c2.getX() - 4, c2.getY() - 4), Point(c2.getX() + 4, c2.getY() + 4))
    r2.setFill("darkblue")
    r2.draw(win)

    return r1, r2

def findCentroid(win, g, c):
    if len(g) > 0 and len(c) > 0:
        total_g_x = 0
        total_g_y = 0
        total_c_x = 0
        total_c_y = 0
        
        for i in g:
            total_g_x += i.getCenter().getX()
            total_g_y += i.getCenter().getY()
        for i in c:
            total_c_x += i.getCenter().getX()
            total_c_y += i.getCenter().getY()

        total_g_x /= len(g)
        total_g_y /= len(g)
        total_c_x /= len(c)
        total_c_y /= len(c)
        
        c1 = Point(total_g_x, total_g_y)
        r1 = Rectangle(Point(c1.getX() - 4, c1.getY() - 4), Point(c1.getX() + 4, c1.getY() + 4))
        r1.setFill("darkred")
        r1.draw(win)
        c2 = Point(total_c_x, total_c_y)
        r2 = Rectangle(Point(c2.getX() - 4, c2.getY() - 4), Point(c2.getX() + 4, c2.getY() + 4))
        r2.setFill("darkblue")
        r2.draw(win)
        
        return r1, r2
    else:
        return initCentroid(win, g, c)

def distance(p1, p2):
    d = math.sqrt((p1.getX()-p2.getX())**2 + (p1.getY()-p2.getY())**2)
    return d

def cluster(g, c, cenG, cenC):
    allPoints = g + c
    newGators = []
    newCrocs = []

    for point in allPoints:
        distG = distance(cenG.getCenter(), point.getCenter())
        distC = distance(cenC.getCenter(), point.getCenter())
        
        if distG < distC:
            newGators.append(point)
            point.setFill("red")
        else:
            newCrocs.append(point)
            point.setFill("blue")
    
    return newGators, newCrocs

def main():
    win = GraphWin("Cluster Example", 800, 800)
    yAx = Line(Point(200, 100), Point(200, 540))
    yAx.draw(win)
    xAx = Line(Point(160, 500), Point(600, 500))
    xAx.draw(win)
    origin = Point(200, 500)
    yLabel = Text(Point(120, 280), "Mass (kg)")
    yLabel.draw(win)
    xLabel = Text(Point(400, 540), "Length (cm)")
    xLabel.draw(win)
    loadButton = Button(win, "Load Data", 80, Point(320, 630))
    loadLabeledButton = Button(win, "Load Labeled", 80, Point(320, 680))
    quitButton = Button(win, "EXIT", 80, Point(140, 630))
    centButton = Button(win, "Centroid", 80, Point(500, 630))
    clusterButton = Button(win, "Cluster", 80, Point(680, 630))

    outputT = Text(Point(300, 50), "Welcome to the Gator / Crocodile Analyzer")
    outputT.draw(win)

    weightT = Text(Point(120, 720), "WEIGHT")
    weightT.draw(win)
    inputWeight = Entry(Point(240, 720), 20)
    inputWeight.draw(win)
    lengthT = Text(Point(360, 720), "LENGTH")
    lengthT.draw(win)
    inputLength = Entry(Point(480, 720), 20)
    inputLength.draw(win)
    inputButton = Button(win, "Test Data", 80, Point(660, 720))

    gators = []
    crocs = []
    cenG = None
    cenC = None

    while True:
        m = win.getMouse()
        if quitButton.isClicked(m):
            win.close()
            break
        
        if loadButton.isClicked(m):
            if cenG:
                cenG.undraw()
                cenC.undraw()
            for g in gators:
                g.undraw()
            for c in crocs:
                c.undraw()
            gators, crocs = loadData(win, False)
            cenG, cenC = initCentroid(win, gators, crocs)
            
        if loadLabeledButton.isClicked(m):
            if cenG:
                cenG.undraw()
                cenC.undraw()
            for g in gators:
                g.undraw()
            for c in crocs:
                c.undraw()
            gators, crocs = loadData(win, True)
            cenG, cenC = initCentroid(win, gators, crocs)

        if centButton.isClicked(m):
            if cenG:
                cenG.undraw()
                cenC.undraw()
            cenG, cenC = findCentroid(win, gators, crocs)

        if clusterButton.isClicked(m):
            gators, crocs = cluster(gators, crocs, cenG, cenC)
            
        if inputButton.isClicked(m):
            try:
                w = float(inputWeight.getText())
                l = float(inputLength.getText())
                p = Point(int(200 + l*100), int(500 - w*1.75))
                d1 = distance(p, cenG.getCenter())
                d2 = distance(p, cenC.getCenter())
                if d1 < d2:
                    outputT.setText("You have an Alligator weighing " + str(w) + " kg and " + str(l) + " meters long")
                else:
                    outputT.setText("You have a Crocodile weighing " + str(w) + " kg and " + str(l) + " meters long")
            except:
                outputT.setText("Please enter valid numbers for weight and length")

if __name__ == "__main__":
    main()
