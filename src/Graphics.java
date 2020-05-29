import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Graphics extends JFrame implements ActionListener, MouseListener {
    JComboBox cbA, cbB;
    JButton btAnadir, btQuitar, btTraspasar1, btTraspasar2;
    JTextField anade, quita;
    JLabel seleccion, cantidad, tiempo; // Quiero **entender** que no te confundes al decir 3 JLabels y uno es para el
                                        // tiempo
    Timer t;
    ArrayList<String> listaA = new ArrayList();
    ArrayList<String> listaB = new ArrayList();
    int seg = 0;
    int min = 0;
    int reset = 0;

    boolean vienesDeMetodo = false;

    public Graphics() {
        super("Ni idea de que va el ejercicio");
        setLayout(null);
        System.out.println(cbA);
        // Combos
        cbA = sacamosPuntosYComas("No; Se; De; Que; Va; Esto", listaA, cbA);
        cbB = sacamosPuntosYComas("Curro; Matiza; Mas; Lo; Que;Quieres", listaB, cbB);
        cbA.setSize(cbA.getPreferredSize().width, cbA.getPreferredSize().height);
        cbB.setSize(cbB.getPreferredSize().width, cbB.getPreferredSize().height);
        cbA.setLocation(50, 0);
        cbB.setLocation(200, 0);
        cbA.setSelectedIndex(0);
        cbB.setSelectedIndex(0);
        cbA.setToolTipText("Lista A");
        cbB.setToolTipText(listaB.get(cbB.getSelectedIndex()));
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
        btQuitar.addMouseListener(this);
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
        seleccion = new JLabel("Componente seleccionado - " + listaA.get(cbA.getSelectedIndex()));
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
            ;
            cbB.removeAllItems();
            ;
            sacamosPuntosYComas("No; Se; De; Que; Va; Esto", listaA, cbA);
            sacamosPuntosYComas("Curro; Matiza; Mas; Lo; Que;Quieres", listaB, cbB);
            anade.setText("");
            quita.setText("");
            cantidad.setText("Cantidad de elementos - " + cbA.getItemCount());
            seleccion.setText("Componente seleccionado - " + listaA.get(cbA.getSelectedIndex()));
        }

        // Ya estaria el timer

        if (e.getSource() == anade || e.getSource() == btAnadir) {
            sacamosPuntosYComas();
            cantidad.setText("Cantidad de elementos - " + cbA.getItemCount());
            seleccion.setText("Componente seleccionado - " + listaA.get(cbA.getSelectedIndex()));
        }

        if (e.getSource() == quita || e.getSource() == btQuitar) {
            quitamosListaA();
            cantidad.setText("Cantidad de elementos - " + cbA.getItemCount());
            seleccion.setText("Componente seleccionado - " + listaA.get(cbA.getSelectedIndex()));
        }

        if (e.getSource() == btTraspasar1) {
            cambio(cbA, cbB);
            cantidad.setText("Cantidad de elementos - " + cbA.getItemCount());
            seleccion.setText("Componente seleccionado - " + listaA.get(cbA.getSelectedIndex()));
        }
        if (e.getSource() == btTraspasar2) {
            cambio(cbB, cbA);
            cantidad.setText("Cantidad de elementos - " + cbA.getItemCount());
            seleccion.setText("Componente seleccionado - " + listaA.get(cbA.getSelectedIndex()));
        }

       if(e.getSource() == cbA){
            if(vienesDeMetodo){
                vienesDeMetodo = false;
            }else{
                seleccion.setText("Componente seleccionado - " + listaA.get(cbA.getSelectedIndex()));
        
            }
       }
    }

    public void quitamosListaA() {
        System.err.println(listaA.size() + "  ---- " + cbA.getItemCount());
        vienesDeMetodo = true;
        if (listaA.size() > 1) {
            String inicio = quita.getText().toUpperCase();

            if (!inicio.equals("")) {
                System.err.println(listaA.size() + "  --B-- " + cbA.getItemCount());
                for (int i = listaA.size() - 1; i >= 0; i--) {
                    if (listaA.size() > 1) {
                        if (listaA.get(i).toUpperCase().startsWith(inicio)) {
                            listaA.remove(i);
                            cbA.removeItemAt(i);
                        }
                    }else{
                        JOptionPane.showMessageDialog(this, "La lista no puede quedar vacia", "Error", 0); // Si, norma mia, no se
                                                                                               // especifica lo
                                                                                               // contrario
                        //Y esta copiao y pegado ¿eh? que desgraciao soy
                    }
                }
            } else {
                System.err.println(listaA.size() + "  --C-- " + cbA.getItemCount());
                listaA.remove(cbA.getSelectedIndex());
                cbA.removeItemAt(cbA.getSelectedIndex());
            }
        } else {
            JOptionPane.showMessageDialog(this, "La lista no puede quedar vacia", "Error", 0); // Si, norma mia, no se
                                                                                               // especifica lo
                                                                                               // contrario
        }
        System.err.println(listaA.size() + "  --D-- " + cbA.getItemCount());
    }

    public void sacamosPuntosYComas() {
        sacamosPuntosYComas(anade.getText(), listaA, cbA);
    }

    public JComboBox sacamosPuntosYComas(String texto, ArrayList<String> lista, JComboBox cajon) {
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
                    lista.add(listaProv[i].trim());
                    if (cajon != null) {
                        cajon.addItem(listaProv[i].trim());
                    } else {
                        cajon = new JComboBox(lista.toArray());
                    }
                }
            }

        } else {
            lista.add(texto.trim());
            if (cajon != null) {
                cajon.addItem(texto.trim());
            } else {
                cajon = new JComboBox(lista.toArray());
            }
        }
        return cajon; // Un poco gitano pero si no, no me lo quiere construir
    }

    public void cambio(JComboBox lista1, JComboBox lista2) {
        if (lista1.getItemCount() > 1) {
            lista2.addItem(lista1.getSelectedItem());
            lista1.removeItemAt(lista1.getSelectedIndex());
            if (lista1 == cbA) {
                listaB.add(listaA.get(lista1.getSelectedIndex()));
                listaA.remove(lista1.getSelectedIndex());
            } else {
                listaA.add(listaB.get(lista1.getSelectedIndex()));
                listaB.remove(lista1.getSelectedIndex());
            }
        } else {
            JOptionPane.showMessageDialog(this, "La lista no puede quedar vacia", "Error", 0); // Si, norma mia, no se
                                                                                               // especifica lo
                                                                                               // contrario
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        btQuitar.setBackground(Color.red);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        btQuitar.setBackground(new JButton().getBackground());

    }

  
}