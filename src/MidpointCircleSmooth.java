import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MidpointCircleSmooth extends JFrame {

    private JTextField txtXc, txtYc, txtRadius;
    private SmoothCanvas canvas;
    private JTextArea logArea;

    public MidpointCircleSmooth() {
        setTitle("Algoritma Midpoint Lingkaran - Kelompok 6");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // ==========================================
        // 1. PANEL KONTROL (ATAS)
        // ==========================================
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(new Color(33, 37, 43));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        inputPanel.setOpaque(false);

        Font fontModern = new Font("Segoe UI", Font.BOLD, 14);

        inputPanel.add(createLabel("Pusat X (xc):", fontModern));
        txtXc = createTextField("0", fontModern);
        inputPanel.add(txtXc);

        inputPanel.add(createLabel("Pusat Y (yc):", fontModern));
        txtYc = createTextField("0", fontModern);
        inputPanel.add(txtYc);

        // Radius default diubah menjadi 150 agar lingkarannya besar dan mulus
        inputPanel.add(createLabel("Radius (r):", fontModern));
        txtRadius = createTextField("150", fontModern); 
        inputPanel.add(txtRadius);

        JButton btnDraw = new JButton("Gambar & Hitung");
        btnDraw.setFont(fontModern);
        btnDraw.setBackground(new Color(40, 167, 69)); 
        btnDraw.setForeground(Color.WHITE);
        btnDraw.setFocusPainted(false);
        inputPanel.add(btnDraw);


        controlPanel.add(inputPanel);
        controlPanel.add(Box.createVerticalStrut(10));

        // ==========================================
        // 2. PANEL LOG KOORDINAT (KANAN)
        // ==========================================
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setPreferredSize(new Dimension(250, 0));
        logPanel.setBackground(new Color(40, 44, 52));
        logPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        logArea.setBackground(new Color(24, 26, 31));
        logArea.setForeground(new Color(152, 195, 121)); 

        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Log Koordinat (Oktan 1)",
                TitledBorder.LEFT, TitledBorder.TOP,
                fontModern, Color.WHITE));

        logPanel.add(scrollPane, BorderLayout.CENTER);

        // ==========================================
        // 3. KANVAS GAMBAR (TENGAH)
        // ==========================================
        canvas = new SmoothCanvas();

        btnDraw.addActionListener(e -> processDrawing());

        add(controlPanel, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
        add(logPanel, BorderLayout.EAST);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(font);
        return lbl;
    }

    private JTextField createTextField(String text, Font font) {
        JTextField tf = new JTextField(text, 5);
        tf.setFont(font);
        tf.setHorizontalAlignment(JTextField.CENTER);
        return tf;
    }

    private void processDrawing() {
        try {
            int xc = Integer.parseInt(txtXc.getText());
            int yc = Integer.parseInt(txtYc.getText());
            int r = Integer.parseInt(txtRadius.getText());

            logArea.setText("Pusat: (" + xc + ", " + yc + ")\nRadius: " + r + "\n\n");
            logArea.append("Titik (x, y):\n");
            logArea.append("------------\n");

            // Mengirim data ke kanvas
            canvas.setParameters(xc, yc, r, logArea);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Masukkan angka bulat yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MidpointCircleSmooth().setVisible(true));
    }
}

// ==========================================
// KELAS KANVAS DENGAN FITUR PANNING (MOUSE DRAG)
// ==========================================
class SmoothCanvas extends JPanel {
    private int xc = 0, yc = 0, r = 0;
    private boolean shouldDraw = false;
    private boolean isFirstDraw = true; // Mencegah log tercetak berulang kali saat di-drag
    private JTextArea logArea;
    
    // Variabel untuk Panning (Geser Layar)
    private int cameraX = 0;
    private int cameraY = 0;
    private int lastMouseX, lastMouseY;

    public SmoothCanvas() {
        setBackground(new Color(245, 245, 245));

        // Listener untuk Mouse Drag (Fitur Geser Layar)
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMouseX = e.getX();
                lastMouseY = e.getY();
                setCursor(new Cursor(Cursor.MOVE_CURSOR)); // Ubah kursor jadi ikon tangan
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // Menghitung jarak geser mouse
                int dx = e.getX() - lastMouseX;
                int dy = e.getY() - lastMouseY;
                
                // Menambahkan jarak ke posisi kamera
                cameraX += dx;
                cameraY += dy;
                
                lastMouseX = e.getX();
                lastMouseY = e.getY();
                
                repaint(); // Gambar ulang kanvas dengan posisi kamera baru
            }
        };

        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    public void setParameters(int xc, int yc, int r, JTextArea logArea) {
        this.xc = xc;
        this.yc = yc;
        this.r = r;
        this.logArea = logArea;
        this.shouldDraw = true;
        this.isFirstDraw = true; // Reset agar bisa nge-log lagi
        
        // Reset posisi kamera ke tengah saat menggambar lingkaran baru
        this.cameraX = 0;
        this.cameraY = 0;
        
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        int width = getWidth();
        int height = getHeight();

        // Pindahkan titik 0,0 ke tengah layar ditambah dengan pergeseran mouse
        g2d.translate((width / 2) + cameraX, (height / 2) + cameraY);

        // 1. GAMBAR GRID & SUMBU X, Y
        drawGridAndAxes(g2d);

        // 2. EKSEKUSI ALGORITMA MIDPOINT
        if (shouldDraw) {
            // Penanda Titik Pusat (Warna Biru)
            g2d.setColor(Color.BLUE);
            g2d.fillRect(xc - 3, -yc - 3, 6, 6);

            // Gambar Lingkaran (Warna Merah)
            g2d.setColor(new Color(224, 82, 99)); 
            drawMidpointCircle(g2d, xc, yc, r);
            
            isFirstDraw = false; // Matikan log setelah iterasi pertama selesai
        }
    }

    private void drawGridAndAxes(Graphics2D g2d) {
        int range = 4000; // Buat grid membentang sangat luas agar tidak habis saat di-drag
        int gridSpacing = 50; // Jarak antar garis grid 50 piksel

        // Garis Grid Tipis
        g2d.setColor(new Color(225, 225, 225));
        for (int i = -range; i <= range; i += gridSpacing) {
            g2d.drawLine(i, -range, i, range);
            g2d.drawLine(-range, i, range, i);
        }

        // Sumbu X dan Y Utama (Tebal)
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(-range, 0, range, 0); // Sumbu X
        g2d.drawLine(0, -range, 0, range); // Sumbu Y
    }

    // --- ALGORITMA MIDPOINT ---
    private void drawMidpointCircle(Graphics2D g, int xc, int yc, int r) {
        int x = 0;
        int y = r;
        int p = 1 - r;

        plotPoints(g, xc, yc, x, y);
        if (isFirstDraw) logArea.append(String.format("(%d, %d)\n", x, y));

        while (x < y) {
            x++;
            if (p < 0) {
                p = p + 2 * x + 1;
            } else {
                y--;
                p = p + 2 * x + 1 - 2 * y;
            }
            plotPoints(g, xc, yc, x, y);
            
            // Catat log hanya pada saat pertama kali tombol ditekan
            if (isFirstDraw) {
                logArea.append(String.format("(%d, %d)\n", x, y));
            }
        }
    }

    private void plotPoints(Graphics2D g, int xc, int yc, int x, int y) {
        drawPixel(g, xc + x, yc + y);
        drawPixel(g, xc - x, yc + y);
        drawPixel(g, xc + x, yc - y);
        drawPixel(g, xc - x, yc - y);
        drawPixel(g, xc + y, yc + x);
        drawPixel(g, xc - y, yc + x);
        drawPixel(g, xc + y, yc - x);
        drawPixel(g, xc - y, yc - x);
    }

    // Gambar piksel sesungguhnya (1:1 Skala Monitor)
    private void drawPixel(Graphics2D g, int x, int y) {
        // Digambar dengan ukuran 2x2 agar garisnya terlihat padat dan jelas, namun tetap mulus
        g.fillRect(x, -y, 2, 2); 
    }
}