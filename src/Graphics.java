import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Graphics extends JFrame implements ActionListener{
    JComboBox cbA, cbB;
    JButton btAnadir, btQuitar, btTraspasar1, btTraspasar2;
    JTextField anade, quita;
    JLabel seleccion, cantidad, tiempo; // Quiero **entender** que no te confundes al decir 3 JLabels y uno es para el
                                        // tiempo
    Timer t;
    int seg = 0;
    int min = 0;
    int reset = 0;

    boolean vienesDeMetodo = false;

    public Graphics() {
        super("Ni idea de que va el ejercicio");
        setLayout(null);
        System.out.println(cbA);
        // Combos
        cbA = new JComboBox<>();
        cbB = new JComboBox<>();
        cbA.setSize(150, cbA.getPreferredSize().height);
        cbB.setSize(150, cbB.getPreferredSize().height);
        cbA.setLocation(50, 0);
        cbB.setLocation(200, 0);
        cbA.setToolTipText("Lista A");
        cbB.setToolTipText("" + cbB.getSelectedIndex());
        add(cbA);
        add(cbB);

        // JButtons
        btAnadir = new JButton("Anadir");
        btAnadir.setSize(btAnadir.getPreferredSize().width, btAnadir.getPreferredSize().height);
        btAnadir.setLocation(50, 75);
        btAnadir.addActionListener(this);
        btAnadir.setToolTipText("Anade un elmento a lista A");
        add(btAnadir);

        btQuitar = new JButton("Quitar");
        btQuitar.setSize(btQuitar.getPreferredSize().width, btQuitar.getPreferredSize().height);
        btQuitar.setLocation(200, 75);
        btQuitar.addActionListener(this);
        btQuitar.addMouseListener(new controlRaton());
        btQuitar.setToolTipText("Quita un elmento de lista A");
        add(btQuitar);

        btTraspasar1 = new JButton("Traspasar a>b");
        btTraspasar1.setSize(btTraspasar1.getPreferredSize().width, btTraspasar1.getPreferredSize().height);
        btTraspasar1.setLocation(50, 100);
        btTraspasar1.addActionListener(this);
        btTraspasar1.setToolTipText("Manda elemento de a a b");
        add(btTraspasar1);

        btTraspasar2 = new JButton("Traspasar b>a");
        btTraspasar2.setSize(btTraspasar2.getPreferredSize().width, btTraspasar2.getPreferredSize().height);
        btTraspasar2.setLocation(200, 100);
        btTraspasar2.addActionListener(this);
        btTraspasar2.setToolTipText("Manda elemento de b a a");
        add(btTraspasar2);

        // TextFields
        anade = new JTextField(10);
        anade.setSize(anade.getPreferredSize().width, anade.getPreferredSize().height);
        anade.setLocation(50, 50);
        anade.addActionListener(this);
        anade.setToolTipText("Elementos a anadir, separar con ';'");
        add(anade);

        quita = new JTextField(10);
        quita.setSize(quita.getPreferredSize().width, quita.getPreferredSize().height);
        quita.setLocation(200, 50);
        quita.addActionListener(this);
        quita.setToolTipText("Quita un elemento que empiece por el texto, si no se rellena borra el seleccionado");
        add(quita);

        // Labels
        seleccion = new JLabel("Componente seleccionado - " + (cbA.getSelectedIndex() + 1));
        seleccion.setSize(seleccion.getPreferredSize().width + 50, seleccion.getPreferredSize().height);
        seleccion.setLocation(50, 150);
        seleccion.setToolTipText("Bastante descriptivo");
        add(seleccion);

        cantidad = new JLabel("Cantidad de elementos - " + cbA.getItemCount());
        cantidad.setSize(cantidad.getPreferredSize().width + 50, cantidad.getPreferredSize().height);
        cantidad.setLocation(50, 200);
        cantidad.setToolTipText("¿No sabes leer?");
        add(cantidad);

        tiempo = new JLabel("00:00"); // No jodas Curro, esto tendria que ser perfectamente valido, que no hay ni
                                      // timer aun
        tiempo.setSize(tiempo.getPreferredSize().width, tiempo.getPreferredSize().height);
        tiempo.setLocation(50, 250);
        tiempo.setToolTipText("A ver figura, es un puto cronometro");
        add(tiempo);

        // Timer
        t = new Timer(1000, this);
        t.start();

        // Dejo esto aqui para que no salte excepcion
        cbA.addActionListener(this);
        cbB.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == t) {
            seg++;
            reset++;
            if (seg == 60) {
                seg = 0;
                min++;
            }
            tiempo.setText(String.format("%02d:%02d", min, seg));
        } else {
            reset = 0;
        }
        if (reset == 60) {
            cbA.removeAllItems();
            cbB.removeAllItems();

            anade.setText("");
            quita.setText("");
            cantidad.setText("Cantidad de elementos - " + cbA.getItemCount());
            seleccion.setText("Componente seleccionado - " + (cbA.getSelectedIndex() + 1));
        }

        // Ya estaria el timer

        if (e.getSource() == anade || e.getSource() == btAnadir) {
            sacamosPuntosYComas();
            cantidad.setText("Cantidad de elementos - " + cbA.getItemCount());
            seleccion.setText("Componente seleccionado - " + (cbA.getSelectedIndex() + 1));
        }

        if (e.getSource() == quita || e.getSource() == btQuitar) {
            quitamosListaA();
            cantidad.setText("Cantidad de elementos - " + cbA.getItemCount());
            seleccion.setText("Componente seleccionado - " + (cbA.getSelectedIndex() + 1));
        }

        if (e.getSource() == btTraspasar1) {
            cambio(cbA, cbB);
            cantidad.setText("Cantidad de elementos - " + cbA.getItemCount());
            seleccion.setText("Componente seleccionado - " + (cbA.getSelectedIndex() + 1));
            cbB.setToolTipText("" + cbB.getSelectedIndex());
        }
        if (e.getSource() == btTraspasar2) {
            cambio(cbB, cbA);
            cantidad.setText("Cantidad de elementos - " + cbA.getItemCount());
            seleccion.setText("Componente seleccionado - " + (cbA.getSelectedIndex() + 1));
        }

        if (e.getSource() == cbA) {
            seleccion.setText("Componente seleccionado - " + (cbA.getSelectedIndex() + 1));
        }

        if (e.getSource() == cbB) {
            cbB.setToolTipText("" + cbB.getSelectedIndex());
        }
    }

    public void quitamosListaA() {
        vienesDeMetodo = true;
        if (cbA.getItemCount() > 0) {
            String inicio = quita.getText().toUpperCase();

            if (!inicio.equals("")) {
                for (int i = cbA.getItemCount() - 1; i >= 0; i--) {

                    if (cbA.getItemAt(i).toString().toUpperCase().startsWith(inicio)) {

                        cbA.removeItemAt(i);

                    }
                }
            } else {
                cbA.removeItemAt(cbA.getSelectedIndex());
            }
        }

    }

    public void sacamosPuntosYComas() {
        sacamosPuntosYComas(anade.getText(), cbA);
    }

    public JComboBox sacamosPuntosYComas(String texto, JComboBox cajon) {
        vienesDeMetodo = true;
        if (texto.contains(";")) {
            boolean tieneAlgo = false;
            String[] listaProv = texto.split(";");
            for (int i = 0; i < listaProv.length; i++) {
                tieneAlgo = false;
                for (int j = 0; j < listaProv[i].length(); j++) {
                    if (listaProv[i].charAt(j) != ' ') {
                        tieneAlgo = true;
                    }
                }
                if (tieneAlgo) {

                    cajon.addItem(listaProv[i].trim());

                }
            }

        }
        cajon.addItem(texto.trim());

        return cajon; // Un poco gitano pero si no, no me lo quiere construir
    }

    public void cambio(JComboBox lista1, JComboBox lista2) {
        if (lista1.getItemCount() > 0) {
            lista2.addItem(lista1.getSelectedItem());
            lista1.removeItemAt(lista1.getSelectedIndex());
        }

    }

    private class controlRaton extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            btQuitar.setBackground(Color.red);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            btQuitar.setBackground(new JButton().getBackground());

        }
    }

}