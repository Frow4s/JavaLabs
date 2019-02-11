package Enums;

public enum Condition {
    SAD,
    IMPRESSED,
    NotIMPRESSED,
    NONE;

    private Condition(){
    }

    public String toString() {
        switch (this) {
             case SAD:
                return "грустно ";
            case IMPRESSED:
                return "удивлён";
            case NotIMPRESSED:
                return "не удивлён";
             default:
                 return "";
        }
    }
}
