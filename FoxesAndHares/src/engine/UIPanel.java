package engine;

import javax.swing.*;
import java.awt.*;

class UIPanel extends JPanel {

    JPanel getComponentWithVerticalTitle(JComponent comp, String label) {
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(0, 1));
        resultPanel.add(new JLabel(label, SwingConstants.CENTER));
        resultPanel.add(comp);
        return resultPanel;
    }

    JPanel getComponentWithHorizontalTitle(JComponent comp, String label) {
        return getComponentWithHorizontalTitle(comp, label, label);
    }

    JPanel getComponentWithHorizontalTitle(JComponent comp, String label, String tooltip) {
        JPanel resultPanel = new JPanel();
        JLabel jlabel = new JLabel(label);
        resultPanel.setLayout(new GridLayout(0,2));
        //jlabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        resultPanel.add(jlabel);
        //comp.setAlignmentX(Component.RIGHT_ALIGNMENT);
        resultPanel.add(comp);
        resultPanel.setToolTipText(tooltip);
        return resultPanel;
    }
}