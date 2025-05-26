package pe.jesusamigo.backend_libreria.role.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Enumeración de permisos con su código y etiqueta legible.
 */
public enum PermissionConstants {

    CREATE_PERSON("CREATE_PERSON", "Crear persona"),
    GET_PERSON("GET_PERSON", "Detalle de persona"),
    GET_PERSONS("GET_PERSONS", "Ver personas"),
    DELETE_PERSON("DELETE_PERSON", "Eliminar persona"),

    GET_PERMISSION("GET_PERMISSION", "Detalle de permiso"),
    GET_PERMISSIONS("GET_PERMISSIONS", "Ver permisos"),

    REMOVE_ROLE_PERMISSION("REMOVE_ROLE_PERMISSION", "Remover permiso de rol"),
    ASSIGN_ROLE_PERMISSION("ASSIGN_ROLE_PERMISSION", "Asignar permisos a rol"),

    CREATE_AUTHOR("CREATE_AUTHOR", "Crear autor"),
    GET_AUTHORS("GET_AUTHORS", "Crear autor"),
    GET_AUTHOR("GET_AUTHOR", "Ver autor"),
    UPDATE_AUTHOR("UPDATE_AUTHOR", "Actualizar autor"),
    DELETE_AUTHOR("DELETE_AUTHOR", "Eliminar autor"),

    CREATE_CATEGORY("CREATE_CATEGORY", "Crear categoría"),
    GET_CATEGORIES("GET_CATEGORIES", "Listar categorías"),
    GET_CATEGORY("GET_CATEGORY", "Obtener categoría"),
    UPDATE_CATEGORY("UPDATE_CATEGORY", "Actualizar categoría"),
    DELETE_CATEGORY("DELETE_CATEGORY", "Eliminar categoría"),

    CREATE_EDITORIAL("CREATE_EDITORIAL", "Crear editorial"),
    GET_EDITORIALS("GET_EDITORIALS", "Listar editoriales"),
    GET_EDITORIAL("GET_EDITORIAL", "Obtener editorial"),
    UPDATE_EDITORIAL("UPDATE_EDITORIAL", "Actualizar editorial"),
    DELETE_EDITORIAL("DELETE_EDITORIAL", "Eliminar editorial"),

    CREATE_PRODUCT("CREATE_PRODUCT", "Crear producto"),
    GET_PRODUCTS("GET_PRODUCTS", "Listar productos"),
    GET_PRODUCT("GET_PRODUCT", "Obtener producto"),
    UPDATE_PRODUCT("UPDATE_PRODUCT", "Actualizar producto"),
    DELETE_PRODUCT("DELETE_PRODUCT", "Eliminar producto"),
    UPDATE_PRODUCT_ACTIVE("UPDATE_PRODUCT_ACTIVE", "Actualizar estado del producto"),
    VIEW_ALL_PRODUCTS("VIEW_ALL_PRODUCTS", "Ver productos"),

    GET_USERS("GET_USERS", "Ver usuarios"),

    CREATE_STOCK_MOVEMENT("CREATE_STOCK_MOVEMENT", "Crear movimiento de stock"),
    UPDATE_STOCK_MOVEMENT("UPDATE_STOCK_MOVEMENT", "Actualizar movimiento de stock"),
    GET_STOCK_MOVEMENTS("GET_STOCK_MOVEMENTS", "Listar movimientos de stock"),
    GET_STOCK_MOVEMENT("GET_STOCK_MOVEMENT", "Obtener movimiento de stock"),

    RECHARGE_STOCK("RECHARGE_STOCK", "Recargar stock"),
    DECREASE_STOCK("DECREASE_STOCK", "Dar de baja stock"),
    GET_PRODUCT_STOCK("GET_PRODUCT_STOCK", "Consultar stock de producto"),

    CREATE_SALE("CREATE_SALE", "Registrar venta"),
    GET_SALES("GET_SALES", "Listar ventas"),
    GET_SALE("GET_SALE", "Obtener venta"),

    REPORTS_VIEW("REPORTS_VIEW","Ver reporte"),

    CREATE_ROLE("CREATE_ROLE", "Crear rol"),
    GET_ROLE("GET_ROLE", "Detalle de rol"),
    GET_ROLES("GET_ROLES", "Ver roles"),
    DELETE_ROLE("DELETE_ROLE", "Eliminar rol"),
    UPDATE_ROLE("UPDATE_ROLE", "Actualizar rol");


    private final String code;
    private final String label;

    private static final Map<String, PermissionConstants> BY_CODE =
            Stream.of(values()).collect(Collectors.toMap(PermissionConstants::getCode, pc -> pc));

    PermissionConstants(String code, String label) {
        this.code = code;
        this.label = label;
    }

    /**
     * @return el identificador interno del permiso (ej. "GET_PERSONS").
     */
    public String getCode() {
        return code;
    }

    /**
     * @return la etiqueta legible para mostrar en UI (ej. "Ver personas").
     */
    public String getLabel() {
        return label;
    }

    /**
     * Busca la constante por su código.
     *
     * @param code el código del permiso
     * @return la constante correspondiente
     * @throws IllegalArgumentException si no existe una constante con ese código
     */
    public static PermissionConstants fromCode(String code) {
        PermissionConstants pc = BY_CODE.get(code);
        if (pc == null) {
            throw new IllegalArgumentException("Permiso desconocido: " + code);
        }
        return pc;
    }
}