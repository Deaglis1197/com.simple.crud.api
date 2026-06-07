package com.simple.crud.api.usermanagement.action.enums;

import org.springframework.stereotype.Component;

public enum ActionType {

    //<editor-fold desc="Actions">
        //<editor-fold desc="Base Controller">
            WRITE_CONSOLE,
            READ_FROM_APP;
        //</editor-fold>
    //</editor-fold>

    @Component("auth")
    public static class Authority {

        //<editor-fold desc="Base Controller Constants">
        public final String WRITE_CONSOLE = ActionType.WRITE_CONSOLE.name();
        public final String READ_FROM_APP = ActionType.READ_FROM_APP.name();
        //</editor-fold>
    }
}
