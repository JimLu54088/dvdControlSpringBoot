package jp.co.csii.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

@Data
public class DVDEntity {
	private int dvdId;
	private String dvdname;
	private String status;
	private LocalDate borrowedDate;
	private int borrowedCount;
	private String ins_by;
	private LocalDate ins_dt;
	private String upd_by;
	private LocalDateTime upd_dt;
}
