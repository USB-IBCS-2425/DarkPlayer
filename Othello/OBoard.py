from Button import*
from Cell import*

class OBoard():

    def __init__(self, win):
        self.cells = []
        for i in range(8):
            for j in range(8):
                if (i+j)%2 == 0:
                    color = "white"
                else:
                    color = "grey"
                self.cells.append(Cell(win, (j, i), color))

        self.potentMoves = []
        self.whoMove = "black"

    def setWhoMove(self, pl):
        self.whoMove = pl

    def getPotentMoves(self):
        return self.potentMoves

    def getAllCells(self):
        return self.cells

    def checkMoves(self, no):
        moves = []
        UL = [-1, -1]
        U = [0, -1]
        UR = [1, -1]
        L = [-1, 0]
        R = [1, 0]
        DL = [-1, 1]
        D = [0, 1]
        DR = [1, 1]
        directions = [UL, U, UR, L, R, DL, D, DR]
        for i in range(64):
            cell = self.cells[i]
            if cell.piece != "":
                if self.whoMove != cell.piece:
                    for d in directions:
                        if (i%8 == 0 and d[0] < 0) or (i%8 == 7 and d[0] > 0):
                            continue
                            
                        newInd = i + d[0] + 8*d[1]
                        
                        if 0 <= newInd < 64:
                            if self.cells[newInd].piece == "":
                                
                                checkForValid = False
                                pos = i
                                for j in range(6):
                                    
                                    pos = pos - d[0] - 8*d[1]
                                    
                                    if pos < 0 or pos >= 64:
                                        break
                                    elif self.cells[pos].piece == "":
                                        break
                                    elif self.cells[pos].piece == self.whoMove:
                                        checkForValid = True
                                        break
                                    elif (pos%8 == 0 and d[0] < 0) or (pos%8 == 7 and d[0] > 0):
                                        break
                                        
                                if checkForValid:
                                    moves.append(self.cells[newInd])
        if no == False:  
            for m in moves:
                m.highlight()
        return moves

    def place(self, c):
        c.updatePiece(self.whoMove)
        toFlip = []
        ind = self.cells.index(c)
        UL = [-1, -8]
        U = [0, -8]
        UR = [1, -8]
        L = [-1, 0]
        R = [1, 0]
        DL = [-1, 8]
        D = [0, 8]
        DR = [1, 8]
        directions = [UL, U, UR, L, R, DL, D, DR]
        for d in directions:
            newInd = ind + d[0] + d[1]
            tempFlip = []
            for i in range(7):
                if (newInd%8 == 7) and (d[0] == -1):
                    break
                if (newInd%8 == 0) and (d[0] == 1):
                    break
                if 0 <= newInd < 64:
                    if (self.cells[newInd].piece != self.whoMove) and (self.cells[newInd].piece != ""):
                        tempFlip.append(newInd)
                    elif (self.cells[newInd].piece == self.whoMove):
                        for t in tempFlip:
                            toFlip.append(t)
                        break
                    else:
                        break
                    newInd = newInd + d[0] + d[1]
        if self.whoMove == "black":
            change = "white"
        else:
            change = "black"
        for f in toFlip:
            #print(f)
            self.cells[f].updatePiece(self.whoMove)
        self.whoMove = change
        
    def placeCorrect(self):
        best_score = -100000000
        best_move = None
        moves = self.checkMoves(False)
        
        for m in moves:
            
            m.tryUpdatePiece("white")
            immediate_killed = self.hypoPlace(m)
            
            position = m.getCoord()
            if isinstance(position, tuple):
                pos_index = position[1] * 8 + position[0]
            else:
                pos_index = position

            position_bonus = 0
            if pos_index in [0, 7, 56, 63]:  # Corners
                position_bonus = 5
            elif pos_index in [1, 6, 8, 15, 48, 55, 57, 62]:  # Positions adjacent to corners
                position_bonus = -3
            elif pos_index in [9, 14, 49, 54]:  # Diagonal from corners
                position_bonus = -4
            elif pos_index % 8 == 0 or pos_index % 8 == 7 or pos_index < 8 or pos_index > 55:  # Edges
                position_bonus = 2
            

            self.whoMove = "black"
            black_moves = self.checkMoves(True)
            

            opponent_best_score = 0
            if black_moves:
                opponent_best_score = 10000000
                for b_move in black_moves:
                    b_move.tryUpdatePiece("black")
                    black_kills = self.hypoPlace(b_move)
                    
   
                    self.whoMove = "white"
                    white_response_moves = self.checkMoves(True)
                    white_response_score = 0
                    
                    if white_response_moves:
                        for w_move in white_response_moves:
                            w_move.tryUpdatePiece("white")
                            white_kills = self.hypoPlace(w_move)
                            
                            sequence_score = immediate_killed + white_kills - black_kills
                            white_response_score = max(white_response_score, sequence_score)
                            
                            w_move.emptyPiece()
                    else:
                        white_response_score = immediate_killed - black_kills
                    
                    opponent_score = -white_response_score
                    opponent_best_score = min(opponent_best_score, opponent_score)
                    
                    b_move.emptyPiece()
            else:

                opponent_best_score = -10  # Bonus for forcing opponent to pass
            
            final_score = immediate_killed + position_bonus - opponent_best_score
            
            m.emptyPiece()
            
            if final_score > best_score:
                best_score = final_score
                best_move = m
        
        self.whoMove = "white"
        
        if best_move:
            self.place(best_move)
        
    def hypoPlace(self, c):
        numKilled = 0
        toFlip = []
        ind = self.cells.index(c)
        UL = [-1, -8]
        U = [0, -8]
        UR = [1, -8]
        L = [-1, 0]
        R = [1, 0]
        DL = [-1, 8]
        D = [0, 8]
        DR = [1, 8]
        directions = [UL, U, UR, L, R, DL, D, DR]
        for d in directions:
            newInd = ind + d[0] + d[1]
            tempFlip = []
            for i in range(7):
                if (newInd%8 == 7) and (d[0] == -1):
                    break
                if (newInd%8 == 0) and (d[0] == 1):
                    break
                if 0 <= newInd < 64:
                    if (self.cells[newInd].piece != self.whoMove) and (self.cells[newInd].piece != ""):
                        tempFlip.append(newInd)
                    elif (self.cells[newInd].piece == self.whoMove):
                        for t in tempFlip:
                            toFlip.append(t)
                        break
                    else:
                        break
                    newInd = newInd + d[0] + d[1]

        if self.whoMove == "black":
            change = "white"
        else:
            change = "black"
        for f in toFlip:
            numKilled += 1
        return numKilled
    def calcScore(self):
        b = 0
        w = 0
        for c in self.cells:
            if c.piece == "black":
                b+=1
            if c.piece == "white":
                w+=1
        return "black: " + str(b) + " -- white: " + str(w)

    def changeT(self):
        if self.whoMove == "black":
            self.whoMove = "white"
        else:
            self.whoMove = "black"
    def isOver(self):
        for c in self.cells:
            if c.piece == "":
                return False
        return True
