package GUI;

import Game.GameState;
import board.Board;
import board.Location;
import board.SquareColor;
import piece.Tool;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class View {
    private final JFrame gameFrame;
    private BoardPanel boardPanel;

    private Color lightTileColor = Color.decode("#FFFACD");
    private Color darkTileColor = Color.decode("#593E1A");
    private final Color markBackground = Color.decode("#D1EDF2");
    private final int markDelay = 1000;
    private final String pieceIconPath;
    private GameState gameState;

    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);


    public View(GameState gameState) {
        this.gameFrame = new JFrame("BlackWidow");
        final JMenuBar tableMenuBar = new JMenuBar();
        populateMenuBar(tableMenuBar);
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameState = gameState;

        this.pieceIconPath = "";
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.boardPanel = new BoardPanel();
        // this.gameSetup = new GameSetup(this.gameFrame, true);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

        this.gameFrame.setVisible(true);
    }

    public List<TilePanel> getTilesPanel() {
        return this.boardPanel.boardTiles;
    }

    public void addTileLisener(TilePanel tilePanel, MouseListener listener) {
        tilePanel.addMouseListener(listener);

    }

    class markListener implements ActionListener{
        private final TilePanel tilePanel;
        markListener(final TilePanel tilePanel){
            super();
            this.tilePanel = tilePanel;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            tilePanel.assignTileColor();
        }
    }

    public void drawBoard() {
        this.boardPanel.drawBoard();
    }

    private void populateMenuBar(JMenuBar tableMenuBar) {
        tableMenuBar.add(CreateFileMenu());
        tableMenuBar.add(createPreferencesMenu());

    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    private JMenu CreateFileMenu() {
        // create file menu, add him listener, and return it.
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(e -> System.out.println(e.toString()));
        fileMenu.add(openPGN);
        return fileMenu;
    }


    private JMenu createPreferencesMenu() {
        final JMenu preferencesMenu = new JMenu("Preferences");

        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip board");
        flipBoardMenuItem.addActionListener(e -> {
            Collections.reverse(boardPanel.boardTiles);
            boardPanel.drawBoard();
        });

        preferencesMenu.add(flipBoardMenuItem);
        preferencesMenu.addSeparator();
        return preferencesMenu;
    }

    public class BoardPanel extends JPanel {

        final List<TilePanel> boardTiles;
        private final int BOARD_SIZE = 8;

        BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (board.File file : board.File.values()) {
                    final TilePanel tilePanel = new TilePanel(this, new Location(file, i));
                    this.boardTiles.add(tilePanel);
                    add(tilePanel);
                }
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setBackground(Color.decode("#8B4726"));
            validate();

        }

        void drawBoard() {
            removeAll();
            for (final TilePanel boardTile : boardTiles) {
                boardTile.drawTile();
                add(boardTile);
            }
            validate();
            repaint();
        }
    }

    public class TilePanel extends JPanel {
        private final Location tileId;

        TilePanel(final BoardPanel boardPanel,
                  final Location tileId) {
            super(new GridBagLayout());
             this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(gameState.getBoard());
            highlightTileBorder();
            validate();
        }

        public Location getTileId() {
            return tileId;
        }

        void setLightTileColor(final Color color) {
            lightTileColor = color;
        }

        void setDarkTileColor(final Color color) {
            darkTileColor = color;
        }

        void drawTile() {
            assignTileColor();
            assignTilePieceIcon(gameState.getBoard());
            // reset when currPiece is null;
            highlightLastMove();
            highlightTileBorder();
            highlightLegals();
            validate();
            repaint();
        }


        private void highlightTileBorder() {
            Tool humanMovedPiece = gameState.getHumanMovedPiece();
            if (humanMovedPiece != null &&
                    humanMovedPiece.getColor().equals(gameState.getCurrentPlayer().getColor()) &&
                    humanMovedPiece.getSquare().getLocation().equals(this.tileId)) {
                setBorder(BorderFactory.createLineBorder(Color.cyan));
            } else {
                setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
        }


        private void highlightLastMove(){
            if(gameState.getLastMove() != null){
                if(gameState.getLastMove().getFirst().equals(this.tileId) ||
                gameState.getLastMove().getSecond().equals(this.tileId))
                    setBackground(Color.PINK);
                Timer timer = new Timer(markDelay, new markListener(this));
                timer.setRepeats(false);
                timer.start();
            }
        }

        private void highlightLegals() {
            // todo - replace older type
            for (final Location location : gameState.getPieceLegalMoves()) {
//            for (final Move move : gameState.getPieceLegalMoves()) {
                if (location.equals(this.tileId)) {
 //                 if (move.getDstLocation().equals(this.tileId)) {
                    try {
                        setBackground(markBackground);
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            Tool tool = board.getLocationMap().get(this.tileId).getTool();
            if (tool != null) {
                try {
                    final BufferedImage image = ImageIO.read(new File(pieceIconPath +
                            tool.getColor().toString().charAt(0) + "" +
                            //tool.getName().charAt(1) +
                            tool.getName().substring(0,2) +
                            ".png"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void assignTileColor() {
            Board chessBoard = gameState.getBoard();
            SquareColor squareColor = chessBoard.getLocationMap().get(this.tileId).getSquareColor();
            setBackground(squareColor.equals(SquareColor.Black) ? lightTileColor : darkTileColor);
        }
    }
}
