package movebuilder.test;

import static org.junit.jupiter.api.Assertions.*;


import board.Chessboard;
import board.ChessboardFactory;
import board.ChessboardFactoryImpl;
import exceptions.IllegalMoveException;
import piece.utils.Move;
import piece.utils.MoveBuilder;
import piece.utils.Name;
import piece.utils.Position;
import pieces.Piece;
import pieces.PieceFactory;
import pieces.PieceFactoryImpl;
import static piece.utils.Side.WHITE;
import static piece.utils.Side.BLACK;
import static piece.utils.Name.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 *
 */
class MoveBuilderTest {
   private final Move moveBuilder = new MoveBuilder();
   private final PieceFactory pieceFact = new PieceFactoryImpl();
   private final ChessboardFactory boardFactory = new ChessboardFactoryImpl();
   private Chessboard chessboard;
   private static List<Piece> list;
   /**
 * @throws IllegalMoveException 
    * 
    */

   @BeforeEach
   void init() {
       list = new ArrayList<Piece>();
   }

   @Test
   void testPawnAdvancementMove() {
       initPawnAdvancementMove();
       try {
        moveBuilder.piece(pieceFact.createPiece(PAWN, new Position(3, 5), WHITE))
           .destination(new Position(3, 4))
           .build(chessboard);
    } catch (IllegalMoveException e) {
        fail();
    }
       assertEquals("d4", moveBuilder.toString());
   }
   private void initPawnAdvancementMove() {
       list.add(pieceFact.createPiece(PAWN, new Position(3, 4), WHITE));
       list.add(pieceFact.createPiece(Name.KING, new Position(1, 1), BLACK));
       list.add(pieceFact.createPiece(Name.KING, new Position(6, 6), WHITE));
       chessboard = boardFactory.createTestCB(list);
   }
   @Test
   void testPromotionMove() {
       initPromotionMove();
       try {
        moveBuilder.piece(pieceFact.createPiece(PAWN, new Position(1, 1), WHITE))
           .promotion(QUEEN)
           .destination(new Position(1, 0))
           .build(chessboard);
    } catch (IllegalMoveException e) {
        fail();
    }
       assertEquals("b8=Q", moveBuilder.toString());
   }
   private void initPromotionMove() {
       list.add(pieceFact.createPiece(PAWN, new Position(1, 1), WHITE));
       list.add(pieceFact.createPiece(Name.KING, new Position(4, 5), BLACK));
       list.add(pieceFact.createPiece(Name.KING, new Position(6, 6), WHITE));
       chessboard = boardFactory.createTestCB(list);
   }
   @Test
   void testCheckMove() {
       initTestCheckMove();
       try {
        moveBuilder.piece(pieceFact.createPiece(QUEEN, new Position(4,5), WHITE))
           .check()
           .destination(new Position(1, 5))
           .build(chessboard);
    } catch (IllegalMoveException e) {
        fail();
    }
       assertEquals("Qb3+", moveBuilder.toString());
   }
   private void initTestCheckMove() {
       list.add(pieceFact.createPiece(PAWN, new Position(3, 4), BLACK));
       list.add(pieceFact.createPiece(QUEEN, new Position(4, 5), WHITE));
       list.add(pieceFact.createPiece(Name.KING, new Position(1, 1), BLACK));
       list.add(pieceFact.createPiece(Name.KING, new Position(6, 6), WHITE));
       chessboard = boardFactory.createTestCB(list);
   }
   @Test
   void testCheckmate() {
       initTestCheckmate();
       try {
           moveBuilder.piece(pieceFact.createPiece(QUEEN, new Position(2, 4), WHITE))
              .checkmate()
              .destination(new Position(1, 5))
              .build(chessboard);
       } catch (IllegalMoveException e) {
           fail();
       }
       assertEquals("Qbb3#", moveBuilder.toString());
   }
   
   private void initTestCheckmate() {
       list.add(pieceFact.createPiece(PAWN, new Position(3, 4), BLACK));
       list.add(pieceFact.createPiece(QUEEN, new Position(2, 4), WHITE));
       list.add(pieceFact.createPiece(QUEEN, new Position(0, 4), WHITE));
       list.add(pieceFact.createPiece(Name.KING, new Position(0, 0), BLACK));
       list.add(pieceFact.createPiece(Name.KING, new Position(6, 6), WHITE));
       chessboard = boardFactory.createTestCB(list);
   }
   @Test
   void testPawnCaptureMove() {
       initPawnCaptureMove();
       try {
        moveBuilder.piece(pieceFact.createPiece(PAWN, new Position(3, 4), BLACK))
           .capture()
           .destination(new Position(4, 5))
           .build(chessboard);
    } catch (IllegalMoveException e) {
        fail();
    }
       assertEquals("dxe3", moveBuilder.toString());
   }
   private void initPawnCaptureMove() {
       list.add(pieceFact.createPiece(PAWN, new Position(3, 4), BLACK));
       list.add(pieceFact.createPiece(PAWN, new Position(4, 5), WHITE));
       list.add(pieceFact.createPiece(Name.KING, new Position(1, 1), BLACK));
       list.add(pieceFact.createPiece(Name.KING, new Position(6, 6), WHITE));
       chessboard = boardFactory.createTestCB(list);
   }
   @Test
   void testBishopMove() {
       initBishopTest();
       try {
        moveBuilder.piece(pieceFact.createPiece(BISHOP, new Position(3, 4), WHITE))
           .destination(new Position(5, 6))
           .build(chessboard);
    } catch (IllegalMoveException e) {
        fail();
    }
       assertEquals("Bf2", moveBuilder.toString());
   }
   private void initBishopTest() {
       list.add(pieceFact.createPiece(BISHOP, new Position(3, 4), WHITE));
       list.add(pieceFact.createPiece(BISHOP, new Position(4, 3), BLACK));
       list.add(pieceFact.createPiece(Name.KING, new Position(1, 1), BLACK));
       list.add(pieceFact.createPiece(Name.KING, new Position(6, 6), WHITE));
       chessboard = boardFactory.createTestCB(list);
}
   @Test
   void testDisambiguousMove() {
       initDisambiguousMoveTest();
       try {
        moveBuilder.piece(pieceFact.createPiece(KNIGHT, new Position(3, 4), WHITE))
           .destination(new Position(4, 2))
           .build(chessboard);
    } catch (IllegalMoveException e) {
        fail();
    }
       assertEquals("N3e6", moveBuilder.toString());
   }

   private void initDisambiguousMoveTest() {
       list.add(pieceFact.createPiece(KNIGHT, new Position(3, 4), WHITE));
       list.add(pieceFact.createPiece(KNIGHT, new Position(5, 4), WHITE));
       list.add(pieceFact.createPiece(Name.KING, new Position(1, 1), BLACK));
       list.add(pieceFact.createPiece(Name.KING, new Position(6, 6), WHITE));
       chessboard = boardFactory.createTestCB(list);
   }

}

