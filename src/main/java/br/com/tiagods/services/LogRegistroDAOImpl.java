package br.com.tiagods.services;

import br.com.tiagods.controller.utils.PersistenciaController;
import br.com.tiagods.model.LogRegistro;
import br.com.tiagods.repository.helpers.LogRegistroImpl;
import javafx.concurrent.Task;

public class LogRegistroDAOImpl extends PersistenciaController {

    public void registrarLog(LogRegistro log) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    loadFactory();
                    LogRegistroImpl impl = new LogRegistroImpl(getManager());
                    impl.save(log);
                } catch (Exception e) {

                } finally {
                    close();
                }
                return null;
            }
        };
        new Thread(task).start();
    }

}
