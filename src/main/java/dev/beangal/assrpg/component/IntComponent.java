package dev.beangal.assrpg.component;

import org.ladysnake.cca.api.v3.component.Component;

public interface IntComponent extends Component {
    int get();
    void set(int val);
    int add(int amount);
}
