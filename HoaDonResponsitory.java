/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import DomainModel.HoaDon;
import DomainModel.NhanVien;
import Utilities.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class HoaDonResponsitory {

    public List<HoaDon> getAll() {
        String query = "SELECT [Id]\n"
                + "      ,[IdKH]\n"
                + "      ,[IdNV]\n"
                + "      ,[Ma]\n"
                + "      ,[NgayTao]\n"
                + "      ,[NgayThanhToan]\n"
                + "      ,[TinhTrang]\n"
                + "      ,[TenNguoiNhan]\n"
                + "      ,[DiaChi]\n"
                + "      ,[Sdt]\n"
                + "  FROM [dbo].[HoaDon]";
        List<HoaDon> list = new ArrayList<>();
        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10));
                list.add(hd);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public boolean add(HoaDon hd) {
        String query = "INSERT INTO [dbo].[HoaDon]\n"
                + "           ([IdNV]\n"
                + "           ,[Ma]\n"
                + "           ,[NgayTao]\n"
                + "           ,[TinhTrang])\n"
                + "     VALUES\n"
                + "           (?,?,GETDATE(),?)";
        int check = 0;
        String maHD = new HoaDonResponsitory().maHD();
        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            ps.setObject(1, "b1521873-f23e-4aec-9642-f8ba10f386ef");
            ps.setObject(2, maHD);
            ps.setObject(3, 0);
            check = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public String maHD() {
        HoaDon hd = new HoaDon();
        List<HoaDon> hdl = new HoaDonResponsitory().getAll();
        List<Integer> hds = new ArrayList<>();
        for (HoaDon hoaDon : hdl) {
            int soHD = Integer.valueOf(hoaDon.getMa().substring(2)) + 1;
            hds.add(soHD);
        }
        int j = 0;
        for (Integer integer : hds) {
            int i = integer;
            if (j < i) {
                j = i;
            }
        }
        return "HD" + j;
    }

    public String getIdHD(String id) {
        String query = "SELECT [Id]\n"
                + "  FROM [dbo].[HoaDon]\n"
                + "  where Ma = ?";
        String a = "";
        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            ps.setObject(1, id);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                a=rs.getString(1);
            }
            return a;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
}
