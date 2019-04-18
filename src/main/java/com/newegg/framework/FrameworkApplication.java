package com.newegg.framework;

import com.newegg.framework.common.collections.IKeyedObject;
import com.newegg.framework.common.collections.KeyedCollection;
import com.newegg.framework.common.data.access.DataCommand;
import com.newegg.framework.common.data.access.DataCommandManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;
import java.util.Collection;

//import org.apache.openejb.jee.KeyedCollection;

//import com.newegg.framework.common.collections.KeyedCollection;

@SpringBootApplication
//@ComponentScan(basePackages = "com.newegg.framework.common.data.access")
public class FrameworkApplication {

    public static void main(String[] args) throws Exception {
        //DataCommandManager.GetDataCommand("a");
        SpringApplication.run(FrameworkApplication.class, args);

        //DatabaseList list = ConfigManager.GetConfig(DatabaseList.class);
/*
        //DataCommandManager.GetDataCommand("s");

        Resource resource = new ClassPathResource("data/DataCommand_Award.config");
        File file = resource.getFile();

        System.out.println(((ClassPathResource) resource).getPath());
        System.out.println(((ClassPathResource) resource).getDescription());
        System.out.println(((ClassPathResource) resource).getFilename());
        System.out.println(((ClassPathResource) resource).getURL());


        System.out.println(new ClassPathResource("classpath:").getPath());

        System.out.println(ClassUtils.getDefaultClassLoader().getResource("").getPath());
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("data/DataCommand_Award.config"));
        String tempPath = Thread.currentThread().getContextClassLoader().getResource("data/DataCommand_Award.config").getPath();
        System.out.println(tempPath.substring(0, tempPath.lastIndexOf("/") + 1));
*/
        //File file2 = new File("D:\\Program Files (x86)\\JetBrains\\IDEA\\demo\\framework\\target\\classes\\configs\\data\\DataCommand_*.config");
        //File[] tempList = file2.listFiles();
        //FileFilter fileFilter = new WildcardFileFilter("Database_*.config");
        //File dir = new File("D:\\Program Files (x86)\\JetBrains\\IDEA\\demo\\src\\main\\resources\\data\\");
        //File[] tempList = dir.listFiles(fileFilter);
        //ArrayList<String> arrayList = new ArrayList<String>();
        //String[] array = new String[tempList.length];
        //array[0] = "a";
        //array[1] = "b";
        // ConfigManager.GetConfig(DatabaseList)
       /* URL url = Thread.currentThread().getContextClassLoader().getResource("/data/Database.config");
        String tempPath = Thread.currentThread().getContextClassLoader().getResource("/data/Database.config").getPath();
*/
/*        File file = new File("D:\\Program Files (x86)\\JetBrains\\IDEA\\demo\\framework\\target\\classes\\configs\\data\\");
        FileFilter fileFilter = new WildcardFileFilter("/data/DataCommand_*.config");
        File[] tempList = file.listFiles(fileFilter);
        System.out.println("-------------------------");
        System.out.println(System.getProperty("user.dir"));
        System.out.println(file.getPath());*/

        //System.out.println(PathUtils.TestPath());

        //DatabaseList dbs = ConfigManager.GetConfig(DatabaseList.class);

        //DataOperationConfiguration commands = ConfigManager.GetConfig(DataOperationConfiguration.class);

        //sqlConnection();

/*
        FrameworkApplication application = new FrameworkApplication();
        KeyedCollection<MyBase> bases = application.Base();
        System.out.println(bases.get("Hunk").ConnectionString);
*/

        /*HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlserver://SSQSQL03\\E5QRY01;databaseName=IPP3;user=neweggmis;password=qa.newegg");
        HikariDataSource ds = new HikariDataSource(config);
        Connection con =  ds.getConnection();
        Statement stmt = con.createStatement();
        String sqlstr = "select top 10 * from ipp3.dbo.customer with(nolock)";
        ResultSet rs = stmt.executeQuery(sqlstr);
        while (rs.next()) {
            System.out.println(rs.getString("CustomerID") + " " + rs.getString("CustomerName"));
        }*/
        //sqlPrepare();

        /*Pattern p=Pattern.compile("@([A-Z]|[a-z]|\\d)+");
        Matcher m=p.matcher("SELECT TOP 1 a.ProductSysNo,a.Quantity FROM dbo.Customer_Gift a WHERE a.CustomerSysNo = @CustomerID AND a.ProductSysNo = @SysNos AND a.Quantity > 0 AND a.Status = '1' AND a.LanguageCode = @LanguageCode AND a.CompanyCode = @CompanyCode AND a.StoreCompanyCode = @StoreCompanyCode ");
        List<String> list = new ArrayList<>();
        System.out.println( m.groupCount());
        while (m.find()) {
            list.add(m.group());
        }
        p.pattern();*/

/*        List<String> a = new ArrayList<String>();
        a.add("Hunk");
        a.add("Jancy");
        ObjectFunction(a);
        List<Integer> b = new ArrayList<Integer>();
        b.add(1);
        b.add(2);
        ObjectFunction2(b);*/
        OrderDA();
    }

    public static void OrderDA() throws SQLException {

        DataCommand dataCommand = DataCommandManager.GetDataCommand("Award_GetScratchCardInfo");
        dataCommand.SetParameterValue("@CustomerSysno", "216607");
        dataCommand.SetParameterValue("@LanguageCode", "zh-CN");
        dataCommand.SetParameterValue("@CompanyCode", "8601");
        dataCommand.SetParameterValue("@StoreCompanyCode", "8601");
        ResultSet set = dataCommand.ExecuteDataSet();




    }

    public static void ObjectFunction(Object obj) {
        if (obj instanceof Collection<?>) {
            Collection<?> col = (Collection<?>) obj;
            col.forEach(name -> {
                System.out.println(name.toString());
            });
        }
    }

    public static void ObjectFunction2(Collection<?> collection) {
        //Collection<?> col = (Collection<?>) obj;
        collection.forEach(name -> {
            System.out.println(name.toString());
        });
    }

    public static void sqlConnection() {
        //server=SSQSQL03\E5QRY01;User ID=neweggmis;Password=qa.newegg;database=IPP3
        String connectionUrl = "jdbc:sqlserver://SSQSQL03\\E5QRY01;databaseName=IPP3;user=neweggmis;password=qa.newegg";

        try {
            Connection con = DriverManager.getConnection(connectionUrl);
            Statement stmt = con.createStatement();
            String sqlstr = "select top 10 * from ipp3.dbo.customer with(nolock)";
            ResultSet rs = stmt.executeQuery(sqlstr);
            while (rs.next()) {
                System.out.println(rs.getString("CustomerID") + " " + rs.getString("CustomerName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //Class.forName("com.mysql.jdbc.Driver");

    }

    public static void sqlPrepare() {
        String connectionUrl = "jdbc:sqlserver://SSQSQL03\\E5QRY01;databaseName=IPP3;user=neweggmis;password=qa.newegg";

        try {
            Connection con = DriverManager.getConnection(connectionUrl);
            //Statement stmt = con.createStatement();
            String sqlstr = "select top 10 CustomerName,CustomerID from ipp3.dbo.customer with(nolock) where sysno in (?)";
            PreparedStatement ps = con.prepareStatement(sqlstr);
            ps.setString(1, "1,2");
            //ps.set

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("CustomerID") + " " + rs.getString("CustomerName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public KeyedCollection<MyBase> Base() {
        KeyedCollection<MyBase> bases = new KeyedCollection<MyBase>();
        MyBase bs1 = new MyBase();
        bs1.setName("Hunk");
        bs1.setConnectionString("Hunk 链接字符串");
        bases.add(bs1);
        MyBase bs2 = new MyBase();
        bs2.setName("jancy");
        bs2.setConnectionString("jancy 链接字符串");
        bases.add(bs2);
        return bases;
    }


    public class MyBase implements IKeyedObject<String> {

        public String Name;
        public String ConnectionString;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getConnectionString() {
            return ConnectionString;
        }

        public void setConnectionString(String connectionString) {
            ConnectionString = connectionString;
        }

        @Override
        public String getKey() {
            return Test.Name;
        }
    }

    public static class Test {
        private static String Name = "Hunk";
    }


}


