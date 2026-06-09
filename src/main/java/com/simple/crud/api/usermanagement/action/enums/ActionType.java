package com.simple.crud.api.usermanagement.action.enums;

import org.springframework.stereotype.Component;

public enum ActionType {

    //<editor-fold desc="Actions">
        //<editor-fold desc="Base Controller">
            WRITE_CONSOLE,
            READ_FROM_APP,
        //</editor-fold>
        //<editor-fold desc="Role Controller">
            READ_ROLE,
            CREATE_ROLE,
            UPDATE_ROLE,
            DELETE_ROLE;
        //</editor-fold>
    //</editor-fold>

    @Component("auth")
    public static class Authority {

        //<editor-fold desc="Base Controller Constants">
        public final String WRITE_CONSOLE = ActionType.WRITE_CONSOLE.name();
        public final String READ_FROM_APP = ActionType.READ_FROM_APP.name();
        //</editor-fold>

        //<editor-fold desc="Role Controller Constants">
        public final String READ_ROLE = ActionType.READ_ROLE.name();
        public final String CREATE_ROLE = ActionType.CREATE_ROLE.name();
        public final String UPDATE_ROLE = ActionType.UPDATE_ROLE.name();
        public final String DELETE_ROLE = ActionType.DELETE_ROLE.name();
        //</editor-fold>
    }
}
