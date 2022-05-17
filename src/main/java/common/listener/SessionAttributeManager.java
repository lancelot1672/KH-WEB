package common.listener;

import javax.servlet.*;
import javax.servlet.http.*;

public class SessionAttributeManager implements HttpSessionAttributeListener {

    public SessionAttributeManager() {
    }


    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        String name = sbe.getName();
        Object value = sbe.getValue();

        System.out.println("> 세션 속성 추가!" + name + " = " + value);
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        String name = sbe.getName();
        Object value = sbe.getValue();

        System.out.println("> 세션 속성 제거!" + name + " = " + value);
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}
