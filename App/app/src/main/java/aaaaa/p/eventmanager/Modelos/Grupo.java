package aaaaa.p.eventmanager.Modelos;

import java.util.List;

public class Grupo {

    private int IdGroup;
    private int IdOwner;
    private String Name;
    private List<Usuarios> listaUsuarios;

    public int getIdGroup() {
        return IdGroup;
    }

    public void setIdGroup(int idGroup) {
        IdGroup = idGroup;
    }

    public int getIdOwner() {
        return IdOwner;
    }

    public void setIdOwner(int idOwner) {
        IdOwner = idOwner;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
