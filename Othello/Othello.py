from graphics import*
from OBoard import*
from Cell import*
from Button import*
from OthelloAI import*

def initialPieces(cb):
    
    cb.cells[27].updatePiece("white")
    cb.cells[28].updatePiece("black")
    cb.cells[35].updatePiece("black")
    cb.cells[36].updatePiece("white")

    

def main():

    win = GraphWin("Othello", 1000, 800)
    
    ob = OBoard(win)
    
    quiButton = Button(win, "Exit", 60, Point(870, 220))
    passButton = Button(win, "PASS", 60, Point(870, 420))
    turnT = Text(Point(850, 320), "white plays")
    turnT.draw(win)
    startButton = Button(win, "", 60, Point(850, 100))
    startT = Text(Point(850, 100), "START")
    startT.draw(win)
    checkT = Text(Point(850,550), "")
    checkT.draw(win)

    while True:
        m1 = win.getMouse()
        if quiButton.isClicked(m1):
            win.close()
            exit()

        if startButton.isClicked(m1):

            startT.setText("RESTART")
                     
            restart = True
            break

    initialPieces(ob)

    over = 0
    
    while True:
        turn = False
        turnT.setText(ob.whoMove + " plays")
        mov = ob.checkMoves(False)
        if len(mov) == 0:
            turnT.setText(ob.whoMove + " has no moves. game over")
            break
        m1 = win.getMouse()

        if quiButton.isClicked(m1):
            win.close()
            break

        if passButton.isClicked(m1):
            turn = True
            over += 1
            ob.changeT()
            if (over == 2):
                turnT.setText(ob.whoMove + " has no moves. game over")
                print(ob.calcScore())
                break
                

        if restart:
            if startButton.isClicked(m1):
                for i in range(64):
                    ob.cells[i].empty()
                initialPieces(ob)
                ob.setWhoMove("white")
                turnT.setText(ob.whoMove + " plays")

        for c in mov:
            if c.isClicked(m1):
                ob.place(c)
                over = 0
                turn = True
                break
        for c in mov:
            c.unHighlight()

        if turn:
            AImov = ob.checkMoves(False)
            if len(AImov) == 0:
                over += 1
                ob.changeT()
            else:
                choice = ob.placeCorrect()
                over = 0
                for m in AImov:
                    m.unHighlight()

        if (over == 2 or ob.isOver()):
            print(ob.calcScore())
            turnT.setText(ob.whoMove + " has no moves. game over")
            break
        

            
                    

if __name__ == "__main__":
    main()
