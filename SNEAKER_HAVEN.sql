CREATE DATABASE SNEAKER_HAVEN
GO
USE SNEAKER_HAVEN
GO


--CREATE TABLE Users
--(
--	Id INT IDENTITY(1,1) PRIMARY KEY,
--	UserName VARCHAR(30) NULL,
--	PassWord VARCHAR(30) NULL,
--	Role INT NULL DEFAULT 1
--)
--GO

--INSERT INTO Users(UserName,PassWord,Role)
--VALUES  ('SA','SA',0),
--		('Hoa','Hoa',1),
--		('Truong','Truong',1),
--		('Thu','Thu',1),
--		('Phuong','Phuong',1),
--		('Quan','Quan',1),
--		('Hoang','Hoang',1)
--GO


CREATE TABLE NhanVien
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	--IdUsers INT IDENTITY(1,1),
	MaNhanVien VARCHAR(10) NULL,
	HoTen NVARCHAR(50) NULL,
	GioiTinh BIT NULL,
	NgaySinh DATE NULL,
	Email VARCHAR(50) NULL,
	CCCD VARCHAR(12) NULL,
	SDT VARCHAR(10) NULL,
	MatKhau VARCHAR(30),
	Luong MONEY NULL,
	DiaChi NVARCHAR(255) NULL,
	ChucVu BIT NULL,
	TrangThai INT NULL DEFAULT 1,

	--FOREIGN KEY(IdUsers) REFERENCES Users(Id)
)
GO

INSERT INTO NhanVien(MaNhanVien,HoTen,GioiTinh,NgaySinh,Email,CCCD,SDT,MatKhau,Luong,DiaChi,ChucVu,TrangThai)
VALUES ('NV001',N'Nguyễn Quang Hòa',1,'2005-3-16','hoa1bg1996@gmail.com','024205005432','0366200005','Hoa',60000,N'Lạng Giang, Bắc Giang',1,1),
	   ('NV002',N'Ngô Văn Trường',1,'2005-3-15','truong@gmail.com','024205005412','0123456789','Truong',50000,N'Hai Bà Trưng, Hà Nội',1,1),
	   ('NV003',N'Phạm Thị Thu',0,'2005-3-14','thu@gmail.com','024205005413','0123456788','Thu',40000,N'Nam Định, Nam Định',1,1),
	   ('NV004',N'Thế Phương',1,'2005-3-13','phuong@gmail.com','024205005423','0123456787','Phuong',30000,N'Đông Anh, Hà Nội',1,1),
	   ('NV005',N'Lê Anh Quân',1,'2005-3-12','quan@gmail.com','024205005422','0123456786','Quan',20000,N'Cầu Giấy, Hà Nội',1,1),
	   ('NV006',N'Đức Hoàng',1,'2005-3-11','hoang@gmail.com','024205005418','0123456785','Hoang',10000,N'Q1, HCM',1,1)
GO



CREATE TABLE DotGiamGia
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Ma NVARCHAR(10),
	TenDot NVARCHAR(30) NULL,
	PhanTramGiamGia FLOAT NULL,
	ThoiGianBatDau DATETIME NOT NULL,
	ThoiGianKetThuc DATETIME NOT NULL,
	TrangThai INT DEFAULT 1
)
GO

INSERT INTO DotGiamGia(Ma,TenDot,PhanTramGiamGia,ThoiGianBatDau,ThoiGianKetThuc)
VALUES('DGG001',N'Đợt 1',10,'2024-7-10','2024-7-12'),
	  ('DGG002',N'Đợt 2',13,'2024-9-10','2024-9-12'),
	  ('DGG003',N'Đợt 3',15,'2024-10-10','2024-10-12')
GO


CREATE TABLE HinhThucThanhToan
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Ma NVARCHAR(10),
	TenHinhThuc INT NULL,
	TrangThai INT NULL DEFAULT 1
)
GO

INSERT INTO HinhThucThanhToan(Ma,TenHinhThuc)
VALUES ('HTTT001',1),
	   ('HTTT002',2),
	   ('HTTT003',3)
GO


CREATE TABLE Hang
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Ma NVARCHAR(10),
	TenHang NVARCHAR(20) NULL,
	TrangThai INT NULL DEFAULT 1
)
GO

INSERT INTO Hang(Ma,TenHang)
VALUES ('H001','ADIDAS'),
	   ('H002','NIKE'),
	   ('H003','PROMAX'),
	   ('H004','PUMA'),
	   ('H005','VANS'),
	   ('H006','REEBOK'),
	   ('H007','CONVERSE'),
	   ('H008','FILA'),
	   ('H009','AICS')
GO


CREATE TABLE ChatLieu
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Ma NVARCHAR(10),
	Loai NVARCHAR(20) NULL,
	TrangThai INT NULL DEFAULT 1
)
GO

INSERT INTO ChatLieu(Ma,Loai)
VALUES ('CL001',N'Da Bò'),
	   ('CL002',N'Da Dê'),
	   ('CL003',N'Da Trâu'),
	   ('CL004',N'Da PU'),
	   ('CL005',N'Da EP')
GO


CREATE TABLE KichThuoc
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Ma NVARCHAR(10),
	Size INT NULL,
	TrangThai INT NULL DEFAULT 1
)
GO

INSERT INTO KichThuoc(Ma,Size)
VALUES ('KT001',30),
	   ('KT002',39),
	   ('KT003',40),
	   ('KT004',41),
	   ('KT005',42)
GO


CREATE TABLE MauSac
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Ma NVARCHAR(10),
	TenMau NVARCHAR(20) NULL,
	TrangThai INT NULL DEFAULT 1
)
GO

INSERT INTO MauSac(Ma,TenMau)
VALUES ('MS001',N'Xanh'),
	   ('MS002',N'Đỏ'),
	   ('MS003',N'Tím'),
	   ('MS004',N'Vàng'),
	   ('MS005',N'Hồng'),
	   ('MS006',N'Nâu'),
	   ('MS007',N'Đen'),
	   ('MS008',N'Trắng')
GO


CREATE TABLE DeGiay
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Ma NVARCHAR(10),
	TenDeGiay NVARCHAR(20) NULL,
	TrangThai INT NULL DEFAULT 1
)
GO

INSERT INTO DeGiay(Ma,TenDeGiay)
VALUES ('DG001',N'Đế Da'),
	   ('DG002',N'Đế Cao Su'),
	   ('DG003',N'Đế Commando'),
	   ('DG004',N'Đế Dainite'),
	   ('DG005',N'Đế Crepe'),
	   ('DG006',N'Đế Ridgeway'),
	   ('DG007',N'Đế Wedge')
GO


CREATE TABLE CoGiay
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Ma NVARCHAR(10),
	TenCoGiay NVARCHAR(40) NULL,
	TrangThai INT NULL DEFAULT 1
)
GO

INSERT INTO CoGiay(Ma,TenCoGiay)
VALUES ('CG001',N'Cổ thấp (Low-top)'),
	   ('CG002',N'Cổ trung (Mid-top)'),
	   ('CG003',N'Cổ cao (High-top)'),
	   ('CG004',N'Cổ giày lười (Slip-on)'),
	   ('CG005',N'Cổ giày bốt (Boot)'),
	   ('CG006',N'Cổ giày Chelsea (Chelsea Boot)')
GO


CREATE TABLE KhoiLuong
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Ma NVARCHAR(10),
	TenKhoiLuong NVARCHAR(20) NULL,
	TrangThai INT NULL DEFAULT 1
)
GO

INSERT INTO KhoiLuong(Ma,TenKhoiLuong)
VALUES ('KL001',N'200 Gram'),
	   ('KL002',N'250 Gram'),
	   ('KL003',N'300 Gram'),
	   ('KL004',N'350 Gram'),
	   ('KL005',N'400 Gram'),
	   ('KL006',N'500 Gram'),
	   ('KL007',N'700 Gram'),
	   ('KL008',N'900 Gram')
GO


CREATE TABLE XuatXu
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Ma NVARCHAR(10),
	NoiXuatXu NVARCHAR(20) NULL,
	TrangThai INT NULL DEFAULT 1
)
GO

INSERT INTO XuatXu(Ma,NoiXuatXu)
VALUES ('XX001',N'Hoa Kỳ'),
	   ('XX002',N'Ý'),
	   ('XX003',N'Đức'),
	   ('XX004',N'Anh'),
	   ('XX005',N'Nhật Bản'),
	   ('XX006',N'Việt Nam')
GO


CREATE TABLE NSX
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Ma NVARCHAR(10),
	TenNSX NVARCHAR(40) NULL,
	TrangThai INT NULL DEFAULT 1
)
GO

INSERT INTO NSX(Ma,TenNSX)
VALUES ('NSX001','Nike, Inc'),
	   ('NSX002','Adidas AG'),
	   ('NSX003','Puma SE'),
	   ('NSX004','Reebok International Limited'),
	   ('NSX005','Converse Inc'),
	   ('NSX006','Vans, Inc'),
	   ('NSX007','Asics Corporation'),
	   ('NSX008','Fila Holdings Corp'),
	   ('NSX009','Under Armour, Inc')
GO


CREATE TABLE HinhAnh
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Ma NVARCHAR(10),
	TenAnh NVARCHAR(30) NULL,
	TrangThai INT NULL DEFAULT 1
)
GO

INSERT INTO HinhAnh(Ma,TenAnh)
VALUES ('HA001',N'Ảnh 1'),
	   ('HA002',N'Ảnh 2'),
	   ('HA003',N'Ảnh 3'),
	   ('HA004',N'Ảnh 4'),
	   ('HA005',N'Ảnh 5'),
	   ('HA006',N'Ảnh 6'),
	   ('HA007',N'Ảnh 7'),
	   ('HA008',N'Ảnh 8')
GO


CREATE TABLE KhachHang
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	MaKH VARCHAR(10) NULL,
	HoTen NVARCHAR(50) NULL,
	GioiTinh BIT NULL,
	Sdt VARCHAR(10) NULL,
	DiaChi NVARCHAR(255) NULL,
	TrangThai INT NULL DEFAULT 1

)
GO

INSERT INTO KhachHang(MaKH,HoTen,GioiTinh,Sdt,DiaChi)
VALUES ('KH001',N'Nguyễn Thị Nhung',1,'032747821',N'Lạng Giang, Bắc Giang'),
	   ('KH002',N'Trần Văn Cảnh',0,'032747812',N'Lạng Giang, Bắc Giang'),
	   ('KH003',N'Nguyễn Tiến Dũng',0,'098747889',N'Hai Bà Trưng, Hà Nội'),
	   ('KH004',N'Bùi Huy Hoàng',0,'098747844',N'Hai Bà Trưng, Hà Nội'),
	   ('KH005',N'Đinh Tiên Hoàng',0,'056747811',N'Cầu Giấy, Hà Nội'),
	   ('KH006',N'Ngô Trường Con',1,'056747828',N'Đông Anh, Hà Nội'),
	   ('KH007',N'Thị Nở',1,'099747899',N'Nam Định, Nam Định'),
	   ('KH008',N'Chí Phèo',0,'099747843',N'Q1, HCM'),
	   ('KH009',N'Bá Kiến',1,'022747851',N'Q2, HCM'),
	   ('KH010',N'Ngô Bá Khá',0,'022747891',N'Q3, HCM'),
	   ('KH011',N'Khách Lẻ',0,'',N'')
GO


CREATE TABLE DiaChi
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	IdKH INT,
	Ma VARCHAR(10) NULL,
	ThanhPho NVARCHAR(20) NULL,
	Quan_Huyen NVARCHAR(20) NULL,
	Xa_Phuong NVARCHAR(20) NULL,
	SoNha NVARCHAR(20) NULL,
	DiaChiChiTiet NVARCHAR(255) NULL,
	TrangThai INT NULL DEFAULT 1,
	
	FOREIGN KEY (IdKH) REFERENCES KhachHang(Id)
)
GO

INSERT INTO DiaChi(IdKH,Ma,ThanhPho,Quan_Huyen,Xa_Phuong,SoNha,DiaChiChiTiet)
VALUES (1,'DC001',N'Bắc Giang',N'Lạng Giang',N'Tiên Lục',N'Số 3',N'Số 3, Tiên Lục, Lạng Giang, Bắc Giang'),
	   (2,'DC002',N'Bắc Giang',N'Lạng Giang',N'Mỹ Hà',N'Số 33',N'Số 33, Mỹ Hà, Lạng Giang, Bắc Giang'),
	   (3,'DC003',N'Hà Nội',N'Hai Bà Trưng',N'Bà Trưng',N'Số 10',N'Số 10, Bà Trưng, Hai Bà Trưng, Hà Nội'),
	   (4,'DC004',N'Hà Nội',N'Hai Bà Trưng',N'Bà Triệu',N'Số 31',N'Số 31, Bà Triệu, Hai Bà Trưng, Hà Nội'),
	   (5,'DC005',N'Hà Nội',N'Cầu Giấy',N'Cầu Giấy 1',N'Số 67',N'Số 67, Cầu Giấy 1, Cầu Giấy, Hà Nội'),
	   (6,'DC006',N'Hà Nội',N'Đông Anh',N'Đông Anh 2',N'Số 77',N'Số 77, Đông Anh, Đông Anh 2, Hà Nội'),
	   (7,'DC007',N'Nam Định',N'Nam Định',N'Lộc hạ',N'Số 32',N'Số 32, Lộc hạ, Nam Định, Nam Định'),
	   (8,'DC008',N'HCM',N'Q1',N'Thủ Dầu 1',N'Số 88',N'Số 88, Thủ Dầu 1, Q1, HCM'),
	   (9,'DC009',N'HCM',N'Q2',N'Thủ Dầu 2',N'Số 99',N'Số 99, Thủ Dầu 2, Q2, HCM'),
	   (10,'DC010',N'HCM',N'Q3',N'Thủ Dầu 3',N'Số 63',N'Số 63, Thủ Dầu 3, Q3, HCM')
GO


CREATE TABLE PhieuGiamGia
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	MaPhieuGiamGia VARCHAR(20),
	TenPhieu NVARCHAR(20),
	SoLuong INT,
	NgayBatDau DATE NOT NULL,
	NgayKetThuc DATE NOT NULL,
	DieuKien MONEY NOT NULL,
	Loai NVARCHAR(20),
	GiaTriToiThieu MONEY NOT NULL,
	GiaTriToiDa MONEY NOT NULL,
	TrangThai NVARCHAR(20)
)
GO

INSERT INTO PhieuGiamGia(MaPhieuGiamGia,TenPhieu,SoLuong,NgayBatDau,NgayKetThuc,DieuKien,Loai,GiaTriToiThieu,GiaTriToiDa,TrangThai)
VALUES ('VOUCHER001',N'Giảm 10 - 20%',20,'2024-7-10','2024-7-25',20000,N'Phần trăm',10,20,N'Đang diễn ra'),
	   ('VOUCHER002',N'Giảm Giá 1000đ',10,'2024-7-10','2024-7-30',20000,N'Số Tiền',500,1000,N'Đang diễn ra'),
	   ('VOUCHER003',N'Giảm 2000đ',5,'2024-9-20','2024-9-25',20000,N'Số Tiền',1500,2000,N'Sắp diễn ra'),
	   ('VOUCHER004',N'Giảm Giá 20%',4,'2024-6-20','2024-6-25',20000,N'Phần trăm',10,20,N'Hết hạn')
GO


CREATE TABLE KhachHangGiamGia
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	IdKH INT NULL,
	IdPGG INT NULL,
	Ma VARCHAR(10) NULL,
	TrangThai INT NULL DEFAULT 1,

	FOREIGN KEY(IdKH) REFERENCES KhachHang(id),
	FOREIGN KEY(IdPGG) REFERENCES PhieuGiamGia(id)
)	
GO

INSERT INTO KhachHangGiamGia(IdKH,IdPGG,Ma)
VALUES (1,1,'KHGG001'),
	   (2,2,'KHGG002'),
	   (3,3,'KHGG003'),
	   (4,4,'KHGG004')
GO


CREATE TABLE HoaDon
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	IdKH INT,
	IdNV INT,
	IdPGG INT,
	IdHTTT INT,
	MaHoaDon VARCHAR(20),
	TenKhachHang NVARCHAR(50),
	NgayTao DATE,
	NgayThanhToan DATE,
	Sdt VARCHAR(10),
	DiaChi NVARCHAR(255),
	TongTien MONEY,
	TongTienKhiGiam MONEY,
	TienMat MONEY,
	TienChuyenKhoan MONEY,
	TrangThai INT DEFAULT 1,


	FOREIGN KEY(IdKH) REFERENCES KhachHang(Id),
	FOREIGN KEY(IdNV) REFERENCES NhanVien(Id),
	FOREIGN KEY(IdPGG) REFERENCES PhieuGiamGia(Id),
	FOREIGN KEY(IdHTTT) REFERENCES HinhThucThanhToan(Id)
)
GO

INSERT INTO HoaDon(IdKH,IdNV,IdPGG,IdHTTT,MaHoaDon,TenKhachHang,NgayTao,NgayThanhToan,Sdt,DiaChi,TongTien,TongTienKhiGiam,TienMat,TienChuyenKhoan)
VALUES (1,1,1,1,'HD001',N'Nguyễn Thị Nhung','2024-7-10','2024-7-10','032747821',N'Lạng Giang, Bắc Giang',20000,16000,10000,1000),
	   (2,2,2,2,'HD002',N'Trần Văn Cảnh','2024-7-10','2024-7-10','032747889',N'Hai Bà Trưng, Hà Nội',200000,160000,15000,8000),
	   (3,3,3,3,'HD003',N'Nguyễn Tiến Dũng','2024-7-10','2024-7-10','098747889',N'Hai Bà Trưng, Hà Nội',30000,25000,40000,8000),
	   (4,4,4,1,'HD004',N'Bùi Huy Hoàng','2024-7-10','2024-7-10','098747844',N'Hai Bà Trưng, Hà Nội',2000,1600,50000,3000),
	   (5,5,4,2,'HD005',N'Đinh Tiên Hoàng','2024-7-10','2024-7-10','056747811',N'Cầu Giấy, Hà Nội',54000,16900,50000,1000),
	   (6,6,3,3,'HD006',N'Ngô Trường Con','2024-7-10','2024-7-10','056747828',N'Đông Anh, Hà Nội',77000,59000,10000,5000),
	   (7,1,2,1,'HD007',N'Thị Nở','2024-7-10','2024-7-10','099747899',N'Nam Định, Nam Định',89000,76000,80000,8000),
	   (8,2,1,2,'HD008',N'Chí Phèo','2024-7-10','2024-7-10','099747843',N'Q1, HCM',130000,100000,60000,3000),
	   (9,3,1,3,'HD009',N'Bá Kiến','2024-7-10','2024-7-10','022747851',N'Q2, HCM',890000,799000,780000,1000),
	   (10,4,3,1,'HD010',N'Ngô Bá Khá','2024-7-10','2024-7-10','022747891',N'Q3, HCM',20000,16000,70000,8000)
GO


CREATE TABLE LichSuHoaDon
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	IdHD INT,
	Ma VARCHAR(10) NULL,
	HanhDongNguoiThaoTac NVARCHAR(50),
	LyDoHuy NVARCHAR(50),
	NgayCapNhat DATE,
	GhiChu NVARCHAR(255),
	TrangThai INT DEFAULT 1,

	FOREIGN KEY(IdHD) REFERENCES HoaDon(Id)
)
GO

INSERT INTO LichSuHoaDon(IdHD,Ma,HanhDongNguoiThaoTac,LyDoHuy,NgayCapNhat,GhiChu)
VALUES (1,'LSHD001',N'Tiền mặt','','2024-7-16',N'Đã Bán Thành công'),
	   (2,'LSHD002',N'Chuyển Khoản','','2024-7-16',N'Đã Bán Thành công'),
	   (3,'LSHD003',N'Kết Hợp',N'Không Mua Hàng','2024-7-16',N'Khách Đã Hủy Đơn'),
	   (4,'LSHD004',N'Tiền mặt',N'Không Mua Hàng','2024-7-16',N'Khách Đã Hủy Đơn'),
	   (5,'LSHD005',N'Chuyển Khoản',N'Không Mua Hàng','2024-7-16',N'Đã Bán Thành công'),
	   (6,'LSHD006',N'Kết Hợp',N'Không Mua Hàng','2024-7-16',N'Khách Đã Hủy Đơn'),
	   (7,'LSHD007',N'Tiền mặt',N'Không Mua Hàng','2024-7-16',N'Đã Bán Thành công'),
	   (8,'LSHD008',N'Chuyển Khoản',N'Không Mua Hàng','2024-7-16',N'Khách Đã Hủy Đơn'),
	   (9,'LSHD009',N'Kết Hợp',N'Không Mua Hàng','2024-7-16',N'Đã Bán Thành công'),
	   (10,'LSHD010',N'Chuyển Khoản',N'Không Mua Hàng','2024-7-16',N'Khách Đã Hủy Đơn')

GO


CREATE TABLE SanPham
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	MaSP VARCHAR(10) NOT NULL,
	TenSanPham NVARCHAR(30) NOT NULL,
	SoLuong int NOT NULL,
	TrangThai INT NULL DEFAULT 1
)
GO

INSERT INTO SanPham(MaSP,TenSanPham,SoLuong)
VALUES ('SP001',N'Giày Thể Thao ADIDAS',5),
	   ('SP002',N'Giày Thể Thao NIKE',15),
	   ('SP003',N'Giày Thể Thao PROMAX',25),
	   ('SP004',N'Giày Thể Thao PUMA',10),
	   ('SP005',N'Giày Thể Thao VANS',50),
	   ('SP006',N'Giày Thể Thao REEBOK',34),
	   ('SP007',N'Giày Thể Thao CONVERSE',2),
	   ('SP008',N'Giày Thể Thao FILA',9),
	   ('SP009',N'Giày Thể Thao AICS',10)
GO


CREATE TABLE ChiTietSanPham
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	IdSP INT,
	--IdHDCT INT,
	IdDGG INT,
	IdHang INT,
	IdChatLieu INT,
	IdKichThuoc INT,
	IdMauSac INT,
	IdDeGiay INT,
	IdCoGiay INT,
	IdHinhAnh INT,
	IdKhoiLuong INT,
	IdXuatXu INT,
	IdNSX INT,
	urlHinhAnh VARCHAR(200),
	MaCTSP VARCHAR(10),
	TenCTSP NVARCHAR(30),
	SoLuong INT,
	DonGia MONEY,
	GhiChu NVARCHAR(255),
	TrangThai INT DEFAULT 1

	FOREIGN KEY(IdSP) REFERENCES SanPham(Id),
	--FOREIGN KEY(IdHDCT) REFERENCES HoaDonChiTiet(Id),
	FOREIGN KEY(IdDGG) REFERENCES DotGiamGia(Id),
	FOREIGN KEY(IdHang) REFERENCES Hang(Id),
	FOREIGN KEY(IdChatLieu) REFERENCES ChatLieu(Id),
	FOREIGN KEY(IdKichThuoc) REFERENCES KichThuoc(Id),
	FOREIGN KEY(IdMauSac) REFERENCES MauSac(Id),
	FOREIGN KEY(IdDeGiay) REFERENCES DeGiay(Id),
	FOREIGN KEY(IdCoGiay) REFERENCES CoGiay(Id),
	FOREIGN KEY(IdHinhAnh) REFERENCES HinhAnh(Id),
	FOREIGN KEY(IdKhoiLuong) REFERENCES KhoiLuong(Id),
	FOREIGN KEY(IdXuatXu) REFERENCES XuatXu(Id),
	FOREIGN KEY(IdNSX) REFERENCES NSX(Id)

)
GO

INSERT INTO ChiTietSanPham(IdSP,IdDGG,IdHang,IdChatLieu,IdKichThuoc,IdMauSac,IdDeGiay,IdCoGiay,IdHinhAnh,IdKhoiLuong,IdXuatXu,IdNSX,MaCTSP,TenCTSP,SoLuong,DonGia,GhiChu,urlHinhAnh)
VALUES (1,1,1,1,1,1,1,1,1,1,1,1,'SPCT001',N'Sản Phẩm Chi Tiết 1',2,16000,N'Hết Nước Chấm','src\img\sanpham\438240666_1591003815032081_1124334168987752937_n.jpg'),
	   (2,1,2,2,2,2,2,2,2,2,2,2,'SPCT002',N'Sản Phẩm Chi Tiết 2',15,160000,N'Hết Nước Chấm','src\img\sanpham\438240666_1591003815032081_1124334168987752937_n.jpg'),
	   (3,1,3,3,3,3,3,3,3,3,3,3,'SPCT003',N'Sản Phẩm Chi Tiết 3',25,25000,N'Hết Nước Chấm','src\img\sanpham\438240666_1591003815032081_1124334168987752937_n.jpg'),
	   (4,2,4,4,4,4,4,4,4,4,4,4,'SPCT004',N'Sản Phẩm Chi Tiết 4',10,1600,N'Hết Nước Chấm','src\img\sanpham\438240666_1591003815032081_1124334168987752937_n.jpg'),
	   (5,2,5,5,5,5,5,5,5,5,5,5,'SPCT005',N'Sản Phẩm Chi Tiết 5',50,16900,N'Hết Nước Chấm','src\img\sanpham\438240666_1591003815032081_1124334168987752937_n.jpg'),
	   (6,2,6,1,1,6,6,6,6,6,6,6,'SPCT006',N'Sản Phẩm Chi Tiết 6',34,59000,N'Hết Nước Chấm','src\img\sanpham\438240666_1591003815032081_1124334168987752937_n.jpg'),
	   (7,3,7,2,2,7,7,1,7,7,1,7,'SPCT007',N'Sản Phẩm Chi Tiết 7',2,76000,N'Hết Nước Chấm','src\img\sanpham\438240666_1591003815032081_1124334168987752937_n.jpg'),
	   (8,3,8,3,3,8,1,2,8,8,2,8,'SPCT008',N'Sản Phẩm Chi Tiết 8',9,100000,N'Hết Nước Chấm','src\img\sanpham\438240666_1591003815032081_1124334168987752937_n.jpg'),
	   (9,3,9,4,4,1,2,3,1,1,3,9,'SPCT009',N'Sản Phẩm Chi Tiết 9',10,799000,N'Hết Nước Chấm','src\img\sanpham\438240666_1591003815032081_1124334168987752937_n.jpg'),
	   (1,1,1,5,5,2,3,4,2,2,4,1,'SPCT010',N'Sản Phẩm Chi Tiết 10',3,16000,N'Hết Nước Chấm','src\img\sanpham\438240666_1591003815032081_1124334168987752937_n.jpg')
GO


CREATE TABLE HoaDonChiTiet
(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	IdHD INT,
	IdSPCT INT,
	Ma VARCHAR(10) NULL,
	SoLuong INT,
	Gia MONEY,
	TrangThai INT DEFAULT 1,

	FOREIGN KEY(IdSPCT) REFERENCES ChiTietSanPham(Id),
	FOREIGN KEY(IdHD) REFERENCES HoaDon(Id)
)
GO

INSERT INTO HoaDonChiTiet(IdHD,IdSPCT,Ma,SoLuong,Gia)
VALUES (1,1,'HDCT001',1,16000),
	   (2,2,'HDCT002',2,160000),
	   (3,3,'HDCT003',3,25000),
	   (4,4,'HDCT004',3,1600),
	   (5,5,'HDCT005',2,16900),
	   (6,6,'HDCT006',1,59000),
	   (7,7,'HDCT007',2,76000),
	   (8,8,'HDCT008',3,100000),
	   (9,9,'HDCT009',2,799000),
	   (10,10,'HDCT010',1,16000)
GO

	--Trạng Thái DEFAULT 1 (1 - hiện lên bảng, 0 - Xóa)

	  --SELECT *FROM Users
	  SELECT *FROM NhanVien
	  SELECT *FROM DotGiamGia
	  SELECT *FROM HinhThucThanhToan
	  
	  SELECT *FROM Hang
	  SELECT *FROM ChatLieu
	  SELECT *FROM KichThuoc
	  SELECT *FROM MauSac
	  SELECT *FROM DeGiay
	  SELECT *FROM CoGiay
	  SELECT *FROM HinhAnh
	  SELECT *FROM KhoiLuong
	  SELECT *FROM XuatXu
	  SELECT *FROM NSX

	  SELECT *FROM KhachHangGiamGia
	  SELECT *FROM KhachHang
	  SELECT *FROM DiaChi
	  SELECT *FROM PhieuGiamGia

	  SELECT *FROM HoaDon
	  SELECT *FROM LichSuHoaDon
	  SELECT *FROM SanPham
	  SELECT *FROM ChiTietSanPham
	  SELECT *FROM HoaDonChiTiet

	  go 
CREATE TRIGGER trigger_insert_update_ctsp
ON ChiTietSanPham
AFTER INSERT, UPDATE
AS
BEGIN
    DECLARE @TongSoLuong INT;
    DECLARE @MaSanPham VARCHAR(10);

    SELECT @MaSanPham = Inserted.IdSP
    FROM Inserted;

    SELECT @TongSoLuong = SUM(SoLuong)
    FROM dbo.ChiTietSanPham
    WHERE IdSP = @MaSanPham;

    UPDATE dbo.SanPham
    SET SoLuong = @TongSoLuong
    WHERE Id = @MaSanPham;
END;

GO

CREATE FUNCTION ft_ton_tai_ctsp_trong_hd
(
    @MaHoaDon VARCHAR(20),
    @MaCTSP VARCHAR(10)
)
RETURNS BIT
AS
BEGIN
    DECLARE @result BIT;

    IF EXISTS
    (
        SELECT 1
        FROM dbo.ChiTietSanPham AS ctsp
            INNER JOIN dbo.HoaDonChiTiet AS hdct
                ON hdct.IdSPCT = ctsp.Id
            INNER JOIN dbo.HoaDon AS hd
                ON hd.Id = hdct.IdHD
        WHERE hd.MaHoaDon = @MaHoaDon
              AND ctsp.MaCTSP = @MaCTSP
    )
    BEGIN
        SET @result = 1;
    END;
    ELSE
    BEGIN
        SET @result = 0;
    END;
    RETURN @result;
END;

GO

CREATE PROCEDURE update_so_luong_cstp_trong_hoa_don
    @MaHoaDon VARCHAR(20),
    @MaCTSP VARCHAR(10),
    @Value INT
AS
BEGIN
    DECLARE @IdHd INT;
    DECLARE @IdCTSP INT;

    SELECT @IdHd = Id
    FROM dbo.HoaDon
    WHERE MaHoaDon = @MaHoaDon;

    SELECT @IdCTSP = Id
    FROM dbo.ChiTietSanPham
    WHERE MaCTSP = @MaCTSP;

    UPDATE dbo.HoaDonChiTiet
    SET SoLuong = SoLuong + @Value
    WHERE IdHD = @IdHd
          AND IdSPCT = @IdCTSP;
END;

GO

CREATE PROCEDURE xoa_ctsp_khoi_hdct
    @MaCTSP VARCHAR(10),
    @MaHD VARCHAR(20)
AS
BEGIN
    DECLARE @IdSPCT INT;

    SELECT @IdSPCT = Id
    FROM dbo.ChiTietSanPham
    WHERE MaCTSP = @MaCTSP;

    DELETE FROM dbo.HoaDonChiTiet
    WHERE IdSPCT = @IdSPCT;
END;

GO

CREATE PROCEDURE pc_update_trang_thai_ngay
    @maVoucher VARCHAR(10),
    @trangThaiNgayOutput NVARCHAR(50) OUTPUT,
    @idVoucher INT OUTPUT
AS
BEGIN
    DECLARE @trangThai INT;
    DECLARE @ngayBatDau DATE;
    DECLARE @ngayKetThuc DATE;

    SELECT @ngayBatDau = ngay_bat_dau,
           @ngayKetThuc = ngay_ket_thuc,
           @trangThai = trang_thai,
           @idVoucher = ID
    FROM Voucher
    WHERE ma_voucher = @maVoucher;

    IF (@trangThai <> 3)
    BEGIN
        IF (@ngayBatDau > GETDATE())
        BEGIN
            SET @trangThaiNgayOutput = N'Sắp diễn ra';
            UPDATE Voucher
            SET trang_thai = 1
            WHERE ma_voucher = @maVoucher;
        END;

        ELSE IF (@ngayBatDau <= GETDATE() AND @ngayKetThuc >= GETDATE())
        BEGIN
            SET @trangThaiNgayOutput = N'Đang diễn ra';
            UPDATE Voucher
            SET trang_thai = 2
            WHERE ma_voucher = @maVoucher;
        END;
        ELSE
        BEGIN
            SET @trangThaiNgayOutput = N'Đã kết thúc';
            UPDATE Voucher
            SET trang_thai = 0
            WHERE ma_voucher = @maVoucher;
        END;
    END;

END;
