package com.splitpay.splitpay.services;

import com.splitpay.splitpay.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBC {
    public static List<Member> getAllMembers() {
        String Consulta = "select nombreusuario, email, telefono, nombregrupo, Deudacolectiva from usuario natural join miembro natural join grupo";
        return new ArrayList<>() {
            {
                try (
                        Connection connex = DriverManager.getConnection(Constantes.THINCONN, Constantes.USERNAME, Constantes.PASSWORD);
                        PreparedStatement ps = connex.prepareStatement(Consulta);
                        ResultSet rs = ps.executeQuery()
                ) {
                    while (rs.next()) {
                        add(new Member(new User(rs.getString("nombreusuario"), rs.getString("email"), rs.getString("telefono")),
                                new Group(rs.getString("nombregrupo")), Double.parseDouble(rs.getString("DeudaColectiva"))));
                    }

                } catch (SQLException ex) {
                    System.out.println("Error de Conexión");
                    ex.printStackTrace();
                }


            }
        };
    }

    public static List<User> getAllUsers() {
        String Consulta = "select nombreusuario, email, telefono from usuario";
        System.out.println("Users requested");
        return new ArrayList<>() {
            {
                try (
                        Connection connex = DriverManager.getConnection(Constantes.THINCONN, Constantes.USERNAME, Constantes.PASSWORD);
                        PreparedStatement ps = connex.prepareStatement(Consulta);
                        ResultSet rs = ps.executeQuery()
                ) {
                    while (rs.next()) {
                        add(new User(rs.getString("nombreusuario"), rs.getString("email"), rs.getString("telefono")));
                    }

                } catch (SQLException ex) {
                    System.out.println("Error de Conexión");
                    ex.printStackTrace();
                }


            }
        };
    }

    public static List<Group> getAllGroups() {
        String Consulta = "select nombregrupo, nombreusuario, email, telefono from grupo join usuario on codigousuario = lider";
        return new ArrayList<>() {
            {
                try (
                        Connection connex = DriverManager.getConnection(Constantes.THINCONN, Constantes.USERNAME, Constantes.PASSWORD);
                        PreparedStatement ps = connex.prepareStatement(Consulta);
                        ResultSet rs = ps.executeQuery()
                ) {
                    while (rs.next()) {
                        add(new Group(rs.getString("nombregrupo"), new User(rs.getString("nombreusuario"), rs.getString("email"), rs.getString("telefono"))));
                    }

                } catch (SQLException ex) {
                    System.out.println("Error de Conexión");
                    ex.printStackTrace();
                }


            }
        };
    }

    public static void createUser(User userToCreate) throws SQLException {
        String consultQuery = "select count(codigousuario) as num from usuario";
        String insertQuery = "insert into usuario values (?,?,?,?)";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(Constantes.THINCONN, Constantes.USERNAME, Constantes.PASSWORD);
            PreparedStatement consultStatement = connection.prepareStatement(consultQuery);
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            ResultSet consultResult = consultStatement.executeQuery();
            connection.setAutoCommit(false);

            while (consultResult.next()) {
                int temp = Integer.parseInt(consultResult.getString("num")) + 1;
                insertStatement.setString(1, userToCreate.getUsername());
                insertStatement.setString(2, userToCreate.getEmail());
                insertStatement.setString(3, userToCreate.getPhone());
                insertStatement.setInt(4, temp);
                insertStatement.executeUpdate();
            }

            connection.commit();
            connection.close();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error de Conexión");
                    ex.printStackTrace();
                }
            }

            System.out.println("Error de Inserción: " + e.getMessage());
            throw e;
        }
    }

    public static void createBill(Bill bill) {
        String Consulta = "select count(codigofactura) as num from facturas";
        String insert = "insert into facturas values (?,?,?)";
        Connection connex = null;
        try {
            connex = DriverManager.getConnection(Constantes.THINCONN, Constantes.USERNAME, Constantes.PASSWORD);
            PreparedStatement ps = connex.prepareStatement(Consulta);
            PreparedStatement ins = connex.prepareStatement(insert);
            ResultSet rs = ps.executeQuery();
            connex.setAutoCommit(false);
            while (rs.next()) {
                int temp = Integer.parseInt(rs.getString("num")) + 1;
                ins.setString(1, bill.getName());
                ins.setDate(2, new java.sql.Date(bill.getDate().getTime()));
                ins.setInt(3, temp);
                ins.executeUpdate();
            }
            connex.commit();
            connex.close();
        } catch (SQLException ex) {
            if (connex != null) {
                try {
                    connex.rollback();
                } catch (SQLException e) {
                    System.out.println("Error de Conexión");
                    ex.printStackTrace();
                }
            }
            System.out.println("Error de Conexión");
            ex.printStackTrace();
        }
    }

    public static int getDebt(Member fromMember, Member toMember) {
        String Consulta = """
                with
                miembroXdeudor (costo, codigodeuda, deudor, creditor, usuariodeudor, nombredeudor, monto) as (
                select costodeuda, codigodeuda, deudor, creditor, codigousuario as usuariodeudor, nombreusuario as nombredeudor, nvl(monto, 0) as monto
                from deuda join miembro on (codigomiembro = deudor) natural join usuario left outer join transaccion on (codigomiembro = envia)),

                miembroXdeudorXcreditor (costo, codigodeuda, deudor, creditor, usuariodeudor, nombredeudor, usuariocreditor, nombrecreditor, nombregrupo, codigogrupo, monto) as (
                select costo, codigodeuda, deudor, creditor, usuariodeudor, nombredeudor, codigousuario as usuariocreditor, nombreusuario as nombrecreditor, nombregrupo, codigogrupo, monto
                from miembroXdeudor join miembro on (codigomiembro = creditor) natural join usuario natural join grupo)

                select costo , sum(monto) as transaccionTotal
                from miembroXdeudorXcreditor
                where nombredeudor = ? and nombrecreditor = ? and nombregrupo = ?group by costo""";

        int deudas = 0;

        try (
                Connection connex = DriverManager.getConnection(Constantes.THINCONN, Constantes.USERNAME, Constantes.PASSWORD);
                PreparedStatement ps = connex.prepareStatement(Consulta)


        ) {
            ps.setString(1, fromMember.getUsername());
            ps.setString(2, toMember.getUsername());
            ps.setString(3, toMember.getGroupName());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                deudas = rs.getInt("costo") - rs.getInt("transaccionTotal");


            }

            rs.close();
            return deudas;


        } catch (SQLException ex) {
            System.out.println("Error de Conexión");
            ex.printStackTrace();
        }
        return deudas;
    }

    public static void createDebt(Bill bill, Debt debt) {
        String Consulta = "select codigofactura from facturas where nombrefactura = ?";
        String insert = "insert into deuda values (?,?,?,?,?)";
        String Consulta2 = "select count(codigodeuda) as num from deuda";
        String Consulta3 = "select codigomiembro from miembro natural join usuario natural join grupo where nombreusuario = ? and nombregrupo = ?";
        String UpdateEnvia = """
                update miembro
                set deudacolectiva = deudacolectiva - (
                                    select costodeuda from deuda join miembro on (deudor = codigomiembro) natural join usuario
                                    where nombreusuario = ? and codigodeuda = ?)
                                    where codigomiembro in (select deudor from deuda where codigodeuda = ?)""";
        String UpdateRecibe = """
                update miembro
                set deudacolectiva = deudacolectiva + (
                                    select costodeuda from deuda join miembro on (creditor = codigomiembro) natural join usuario
                                    where nombreusuario = ? and codigodeuda = ?)
                                    where codigomiembro in (select creditor from deuda where codigodeuda = ?)""";
        Connection connex = null;
        try {
            connex = DriverManager.getConnection(Constantes.THINCONN, Constantes.USERNAME, Constantes.PASSWORD);
            PreparedStatement ps = connex.prepareStatement(Consulta);
            PreparedStatement codD = connex.prepareStatement(Consulta2);
            PreparedStatement deudor = connex.prepareStatement(Consulta3);
            PreparedStatement creditor = connex.prepareStatement(Consulta3);
            PreparedStatement ins = connex.prepareStatement(insert);
            PreparedStatement update1 = connex.prepareStatement(UpdateEnvia);
            PreparedStatement update2 = connex.prepareStatement(UpdateRecibe);
            ResultSet cd = codD.executeQuery();
            connex.setAutoCommit(false);
            ps.setString(1, bill.getName());
            ResultSet rs = ps.executeQuery();
            deudor.setString(1, debt.getOwingName());
            deudor.setString(2, debt.getOwing().getGroupName());
            creditor.setString(1, debt.getCreditor().getUsername());
            creditor.setString(2, debt.getCreditor().getGroupName());
            ResultSet CodDeudor = deudor.executeQuery();
            ResultSet CodCreditor = creditor.executeQuery();
            cd.next();
            CodDeudor.next();
            CodCreditor.next();
            rs.next();
            int codigoDeuda = cd.getInt("num") + 1;
            int CodigoDeudor = CodDeudor.getInt("codigomiembro");
            int CodigoCreditor = CodCreditor.getInt("codigomiembro");
            int CodigoFactura = rs.getInt("codigofactura");
            ins.setInt(1, debt.getDebtCost());
            ins.setInt(2, codigoDeuda);
            ins.setInt(3, CodigoDeudor);
            ins.setInt(4, CodigoCreditor);
            ins.setInt(5, CodigoFactura);
            ins.executeUpdate();

            update1.setString(1, debt.getOwing().getUsername());
            update1.setInt(2, codigoDeuda);
            update1.setInt(3, codigoDeuda);
            update1.executeUpdate();

            update2.setString(1, debt.getCreditor().getUsername());
            update2.setInt(2, codigoDeuda);
            update2.setInt(3, codigoDeuda);
            update2.executeUpdate();


            connex.commit();
            connex.close();

        } catch (SQLException ex) {
            if (connex != null) {
                try {
                    connex.rollback();
                } catch (SQLException e) {
                    System.out.println("Error de Conexión");
                    ex.printStackTrace();
                }
            }
            System.out.println("Error de Conexión");
            ex.printStackTrace();
        }
    }

    public static void createTransaction(Transaction transaction) {
        String insert = "insert into transaccion values (?,?,?,?)";
        String Consulta = "select count(codigotransaccion) as num from transaccion";
        String Consulta2 = "select codigomiembro from miembro natural join usuario natural join grupo where nombreusuario = ? and nombregrupo = ?";
        String UpdateEnvia = """
                update miembro
                set deudacolectiva = deudacolectiva + (
                                    select monto from transaccion join miembro on (envia = codigomiembro) natural join usuario
                                    where nombreusuario = ? and codigotransaccion = ?)
                                    where codigomiembro in (select envia from transaccion where codigotransaccion = ?)""";
        String UpdateRecibe = """
                update miembro
                set deudacolectiva = deudacolectiva - (
                                    select monto from transaccion join miembro on (recibe = codigomiembro) natural join usuario
                                    where nombreusuario = ? and codigotransaccion = ?)
                                    where codigomiembro in (select recibe from transaccion where codigotransaccion = ?)""";
        Connection connex = null;
        try {
            connex = DriverManager.getConnection(Constantes.THINCONN, Constantes.USERNAME, Constantes.PASSWORD);
            PreparedStatement codT = connex.prepareStatement(Consulta);
            PreparedStatement from = connex.prepareStatement(Consulta2);
            PreparedStatement to = connex.prepareStatement(Consulta2);
            PreparedStatement ins = connex.prepareStatement(insert);
            PreparedStatement update1 = connex.prepareStatement(UpdateEnvia);
            PreparedStatement update2 = connex.prepareStatement(UpdateRecibe);
            ResultSet cd = codT.executeQuery();
            from.setString(1, transaction.getFromMember().getUsername());
            from.setString(2, transaction.getFromMember().getGroupName());
            to.setString(1, transaction.getToMember().getUsername());
            to.setString(2, transaction.getToMember().getGroupName());
            ResultSet CodEnvia = from.executeQuery();
            ResultSet CodRecibe = to.executeQuery();
            connex.setAutoCommit(false);

            cd.next();
            CodEnvia.next();
            CodRecibe.next();

            int codigoTransaccion = cd.getInt("num") + 1;

            int CodigoEnvia = CodEnvia.getInt("codigomiembro");

            int CodigoRecibe = CodRecibe.getInt("codigomiembro");

            ins.setInt(1, codigoTransaccion);

            ins.setInt(2, transaction.getAmount());

            ins.setInt(3, CodigoEnvia);

            ins.setInt(4, CodigoRecibe);

            ins.executeUpdate();

            update1.setString(1, transaction.getFromMember().getUsername());
            update1.setInt(2, codigoTransaccion);
            update1.setInt(3, codigoTransaccion);
            update1.executeUpdate();

            update2.setString(1, transaction.getToMember().getUsername());
            update2.setInt(2, codigoTransaccion);
            update2.setInt(3, codigoTransaccion);
            update2.executeUpdate();


            connex.commit();
            connex.close();
        } catch (SQLException ex) {

            if (connex != null) {
                try {
                    connex.rollback();
                } catch (SQLException e) {
                    System.out.println("Error de Conexión");
                    ex.printStackTrace();
                }
            }
            System.out.println("Error de Conexión");
            ex.printStackTrace();
        }
    }

    public static void createGroup(Group groupToCreate) {
        String Consulta = "select count(codigogrupo) as num from grupo";
        String Consulta2 = "select codigousuario from usuario where nombreusuario = ?";
        String insert = "insert into grupo values (?,?,?)";
        Connection connex = null;
        try {
            connex = DriverManager.getConnection(Constantes.THINCONN, Constantes.USERNAME, Constantes.PASSWORD);
            PreparedStatement ps = connex.prepareStatement(Consulta);
            PreparedStatement ins = connex.prepareStatement(insert);
            PreparedStatement lid = connex.prepareStatement(Consulta2);

            ResultSet rs = ps.executeQuery();
            connex.setAutoCommit(false);
            while (rs.next()) {
                lid.setString(1, groupToCreate.getLeader().getUsername());
                ResultSet ls = lid.executeQuery();
                while (ls.next()) {
                    int temp = Integer.parseInt(rs.getString("num")) + 1;
                    ins.setString(1, groupToCreate.getName());
                    ins.setInt(2, ls.getInt("codigousuario"));
                    ins.setInt(3, temp);
                    ins.executeUpdate();
                }
                ls.close();
            }

            connex.commit();
            connex.close();
        } catch (SQLException ex) {
            if (connex != null) {
                try {
                    connex.rollback();
                } catch (SQLException e) {
                    System.out.println("Error de Conexión");
                    ex.printStackTrace();
                }
            }
            System.out.println("Error de Conexión");
            ex.printStackTrace();
        }
    }

    public static void createMember(Member memberToCreate) {
        String Consulta = "select count(codigomiembro) as num from miembro";
        String Consulta2 = "select codigousuario from usuario where nombreusuario = ?";
        String Consulta3 = "select codigogrupo from grupo where nombregrupo = ?";
        String insert = "insert into miembro values (?,?,?,?)";
        Connection connex = null;
        try {
            connex = DriverManager.getConnection(Constantes.THINCONN, Constantes.USERNAME, Constantes.PASSWORD);
            PreparedStatement ps = connex.prepareStatement(Consulta);
            PreparedStatement ins = connex.prepareStatement(insert);
            PreparedStatement cu = connex.prepareStatement(Consulta2);
            PreparedStatement cg = connex.prepareStatement(Consulta3);
            ResultSet rs = ps.executeQuery();
            connex.setAutoCommit(false);
            while (rs.next()) {
                cu.setString(1, memberToCreate.getUser().getUsername());
                ResultSet codU = cu.executeQuery();
                while (codU.next()) {
                    cg.setString(1, memberToCreate.getGroupName());
                    ResultSet codG = cg.executeQuery();
                    while (codG.next()) {
                        int temp = Integer.parseInt(rs.getString("num")) + 1;
                        ins.setDouble(1, 0);
                        ins.setInt(2, temp);
                        ins.setInt(3, codU.getInt("codigousuario"));
                        ins.setInt(4, codG.getInt("codigogrupo"));
                    }
                    codG.close();
                }

                codU.close();
                ins.executeUpdate();
            }
            connex.commit();
            connex.close();
        } catch (SQLException ex) {
            if (connex != null) {
                try {
                    connex.rollback();
                } catch (SQLException e) {
                    System.out.println("Error de Conexión");
                    ex.printStackTrace();
                }
            }
            System.out.println("Error de Conexión");
            ex.printStackTrace();
        }
    }
}
