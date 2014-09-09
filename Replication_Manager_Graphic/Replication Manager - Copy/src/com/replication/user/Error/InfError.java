/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.user.Error;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.ModernBalloonStyle;
import net.java.balloontip.utils.FadingUtils;
import net.java.balloontip.utils.TimingUtils;

/**
 *
 * @author Walter
 */
public class InfError {

    static public void showMessageUp(JComponent element, String error) {

        Color color = new java.awt.Color(204, 204, 204);
        BalloonTipStyle look = new ModernBalloonStyle(0, 0, color, color, color);
        BalloonTip myBalloonTip = new BalloonTip(element, error, look, false);
        myBalloonTip.setPadding(3);
        TimingUtils.showTimedBalloon(myBalloonTip, 4000);
        FadingUtils.fadeInBalloon(myBalloonTip, null, 300, 30);
    }

    /**
     * Muestra un mensaje de error debajo del componente de interfaz grafica
     * indicado
     *
     * @param element
     * @param error
     */
    static public void showMessageDown(JComponent element, String error) {

        Color color = new java.awt.Color(204, 204, 204);
        BalloonTipStyle look = new ModernBalloonStyle(0, 0, color, color,
                color);

        BalloonTip myBalloonTip = new BalloonTip(element,
                new JLabel(error), look, BalloonTip.Orientation.LEFT_BELOW,
                BalloonTip.AttachLocation.SOUTHWEST, 10, 8, false);

        myBalloonTip.setPadding(3);
        TimingUtils.showTimedBalloon(myBalloonTip, 4000);
        FadingUtils.fadeInBalloon(myBalloonTip, null, 300, 30);
    }

    static public void showInformation(Component comp, String msg) {

        ImageIcon icon = new ImageIcon(InfError.class.getResource("/com/replication/user/images/info.png"));
        JOptionPane.showMessageDialog(comp, msg, "Información", JOptionPane.INFORMATION_MESSAGE, icon);
    }

    static public int showQuestion(Component comp, String msg) {

        ImageIcon icon = new ImageIcon(InfError.class.getResource("/com/replication/user/images/question.png"));
        int opcion = JOptionPane.showConfirmDialog(comp, msg, "Pregunta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
        return opcion;
    }

    static public void showMessage(Component comp, String msg) {
        ImageIcon icon = new ImageIcon(InfError.class.getResource("/com/replication/user/images/idea.png"));
        JOptionPane.showMessageDialog(comp, msg, "", JOptionPane.INFORMATION_MESSAGE, icon);

    }

    static public int showYesNO(Component comp, String msg) {

        ImageIcon icon = new ImageIcon(InfError.class.getResource("/com/replication/user/images/question.png"));
        int opcion = JOptionPane.showConfirmDialog(comp, msg, "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
        return opcion;
    }
}
