package off_assoc.swing;


import com.formdev.flatlaf.FlatDarculaLaf;
import off_assoc.swing.gui.OffForm;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class OffSwing {
	public static void main(String[] args) {
		FlatDarculaLaf.setup();
		ConfigurableApplicationContext springContext =
				new SpringApplicationBuilder(OffSwing.class)
				.headless(false)
				.web(WebApplicationType.NONE)
				.run(args);

		SwingUtilities.invokeLater(()->{
			OffForm offForm = springContext.getBean(OffForm.class);
			offForm.setVisible(true);
		});
	}

}
