package oe.roma.photodoc.utils;

/**
 * Created by us9522 on 25.08.2015.
 */
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by us8610 on 26.12.2014.
 */
@Service
public class ADAuthenticator {
    private String domain;
    private String ldapHost;
    private String searchBase;

    public ADAuthenticator() {
        this.domain = "ifobl";
        this.ldapHost = "ldap://10.93.1.59";
        this.searchBase = "DC=ifobl,DC=if,DC=energy,DC=gov,DC=ua";
    }

    public ADAuthenticator(String domain, String host, String dn) {
        this.domain = domain;
        this.ldapHost = host;
        this.searchBase = dn;
    }

    public Map authenticate(String user, String pass) {
        String returnedAtts[] = {"sn", "givenName", "mail", "displayName", "sAMAccountName"};
        String searchFilter = "(&(objectClass=user)(sAMAccountName=" + user + "))";

        //Create the search controls
        SearchControls searchCtls = new SearchControls();
        searchCtls.setReturningAttributes(returnedAtts);

        //Specify the search scope
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapHost);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, user + "@" + domain);
        env.put(Context.SECURITY_CREDENTIALS, pass);

        LdapContext ctxGC = null;

        try {
            ctxGC = new InitialLdapContext(env, null);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        //Search objects in GC using filters
        NamingEnumeration answer = null;
        try {
            answer = ctxGC.search(searchBase, searchFilter, searchCtls);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        while (answer.hasMoreElements()) {
                SearchResult sr = null;
                try {
                    sr = (SearchResult) answer.next();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
                Attributes attrs = sr.getAttributes();
                Map amap = null;
                if (attrs != null) {
                    amap = new HashMap();
                    NamingEnumeration ne = attrs.getAll();
                    try {
                        while (ne.hasMore()) {
                            Attribute attr = (Attribute) ne.next();
                            amap.put(attr.getID(), attr.get());
                            System.out.println(attr.getID());
                            System.out.println(attr.get());
                        }
                    } catch (NamingException e) {
                        e.printStackTrace();
                    }
                    try {
                        ne.close();
                    } catch (NamingException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(amap.toString());
                return amap;
            }
        //пусту мапу повертати не можна тому що залогінитись можна буде без пароля
        Map newMap = new HashMap();
        newMap.put("Бойчук Дмитро Михайлович","us9522");
        return newMap;
        ///////////////////////////////////////////////////////////////////////////
    }

}