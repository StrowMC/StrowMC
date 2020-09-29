package fr.strow.persistence.dao.permissions;

import com.google.inject.Inject;
import fr.strow.persistence.beans.permissions.PermissionsBean;
import fr.strow.persistence.beans.permissions.ProxyPermissionsBean;

public class PermissionsDao {

    private final ProxyPermissionsDao proxyPermissionsDao;

    @Inject
    public PermissionsDao(ProxyPermissionsDao proxyPermissionsDao) {
        this.proxyPermissionsDao = proxyPermissionsDao;
    }

    public PermissionsBean loadPermissions(int roleId) {
        ProxyPermissionsBean proxyPermissionsBean = proxyPermissionsDao.loadPermissions(roleId);

        return new PermissionsBean(proxyPermissionsBean);
    }
}
