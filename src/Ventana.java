import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Ventana extends JFrame {
    private Timer temporizador;
    private JPanel panel;
    private JButton b1,b2,b3;
    private JLabel cronometro,tiempo;
    private JLabel fondo,cronom;
    private int centesimaSegundos=0,segundos=0,minutos=0,horas=0;
    //con esto podremos implementar las acciones que ocurrirán cada 10 ms
//en nuestro timer.
    private ActionListener acciones = e -> {
        centesimaSegundos++; //10ms es una centésima.
        if(centesimaSegundos==64){ //por algún motivo, para que el timer sea exacto, 64 centésimas es lo correcto, y no 100.
            segundos++;
            centesimaSegundos=0; //se restablece el contador.
        }
        if(segundos==60){ //60 segundos es un minuto
            minutos++;
            segundos=0; //se restablece el segundero
        }
        if(minutos==60){ //60 minutos es una hora
            horas++;
            minutos=0; //se restablece el minutero.
        }
        actualizarTiempo();
    };
    private void actualizarTiempo(){
        String texto=(horas<=9?"0":"")+horas+":"+(minutos<=9?"0":"")+minutos+":"+(segundos<=9?"0":"")+segundos+"."+(centesimaSegundos<=9?"0":"")+centesimaSegundos;
        //Con esto concatenamos la etiqueta para que muestre 0 delante de los números de tiempo normales (09 en lugar de 9 a secas)
        tiempo.setText(texto);
    }
    public Ventana(){
        setSize(700,300);
        setTitle("Cronómetro");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        temporizador = new Timer(1,acciones);
        iniciarComponentes();
    }
    private void iniciarComponentes(){
        iniciarPanel();
        iniciarBotones();
        colocarEtiqueta();
    }
    private void iniciarPanel(){
        panel = new JPanel();
        panel.setLayout(null);
        this.add(panel);
    }
    private void iniciarBotones(){
        b1 = new JButton("Iniciar");
        b1.setBounds(300,200,110,30);
        b1.setFont(new Font("Arial", Font.PLAIN,18));
        b1.setBackground(new Color(0,204,204));
        panel.add(b1);

        ActionListener iniciar = e -> {
            temporizador.start();
            b1.setEnabled(false);
            b1.setText("Reanudar");
            b1.setFont(new Font("Arial", Font.PLAIN,15));
            b2.setEnabled(true);
            b3.setEnabled(true);
        };
        b1.addActionListener(iniciar);

        b2 = new JButton("Pausar");
        b2.setBounds(425,200,110,30);
        b2.setFont(new Font("Arial", Font.PLAIN,18));
        b2.setBackground(new Color(0,204,204));
        b2.setEnabled(false);
        panel.add(b2);

        ActionListener pausar = e -> {
            temporizador.stop();
            b1.setEnabled(true);
            b2.setEnabled(false);
        };
        b2.addActionListener(pausar);

        b3 = new JButton("Detener");
        b3.setBounds(550,200,110,30);
        b3.setFont(new Font("Arial", Font.PLAIN,18));
        b3.setBackground(new Color(0,204,204));
        b3.setEnabled(false);
        panel.add(b3);
        ActionListener detener = e -> {
            if(temporizador.isRunning()){
                temporizador.stop();
            }
            b1.setEnabled(true);
            b2.setEnabled(false);
            b3.setEnabled(false);
            b1.setFont(new Font("Arial", Font.PLAIN,18));
            b1.setText("Iniciar");
            segundos=0;
            centesimaSegundos=0;
            minutos=0;
            horas=0;

            actualizarTiempo();
        };
        b3.addActionListener(detener);
    }

    private void colocarEtiqueta(){
        cronometro = new JLabel();
        cronometro.setText("CRONÓMETRO");
        cronometro.setBounds(300,20,400,50);
        cronometro.setFont(new Font("Arial",Font.PLAIN,30));
        cronometro.setForeground(Color.BLACK);
        panel.add(cronometro);

        tiempo = new JLabel();
        tiempo.setText("00:00:00.00");
        tiempo.setBounds(300,100,400,40);
        tiempo.setFont(new Font("Arial",Font.PLAIN,45));
        tiempo.setForeground(Color.black);
        panel.add(tiempo);

        ImageIcon crono = new ImageIcon("d:\\prueba\\crono.png");
        cronom = new JLabel(crono);
        cronom.setSize(150,150);
        cronom.setBounds(25,25,200,200);
        cronom.setBackground(null);
        cronom.setIcon(new ImageIcon(crono.getImage().getScaledInstance(cronom.getWidth(),cronom.getHeight(),Image.SCALE_SMOOTH)));
        panel.add(cronom);

        ImageIcon imagen = new ImageIcon("d:\\prueba\\grid.jpg");
        fondo = new JLabel(imagen);
        fondo.setSize(700,300);
        fondo.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(fondo.getWidth(),fondo.getHeight(),Image.SCALE_SMOOTH)));
        panel.add(fondo);
    }
}