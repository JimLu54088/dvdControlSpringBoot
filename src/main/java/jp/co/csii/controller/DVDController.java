package jp.co.csii.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jp.co.csii.entity.DVDEntity;
import jp.co.csii.service.DVDService;

@Controller
public class DVDController {

	@Autowired
	private DVDService dVDService;

	@RequestMapping(value = "/getsubject")
	public String getsubject(Model model) {
		// 給予初始化狀態
		List<String> emptyList = new ArrayList<>();
		model.addAttribute("ls", emptyList);

		return "dvdkanri";

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String search(@RequestParam(value = "dvdname") String dvdname, Model model) {

		List<DVDEntity> ls = dVDService.findByName(dvdname);

		// 沒分頁用
		model.addAttribute("ls", ls);

		return "dvdkanri";

	}

	@RequestMapping(value = "/checkifdatafull", method = RequestMethod.GET)
	public String checkifdatafull(Model model) {

		List<DVDEntity> ls = dVDService.findByName("");

		int cntOfLs = ls.size();

		if (cntOfLs < 10) {
			return "dvdhinki";
		} else {
			// 沒分頁用
			model.addAttribute("ls", ls);

			model.addAttribute("additionalTextForAdding", "DVD list is full. Cannot insert. Limit is 10");

			return "dvdkanri";

		}

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute DVDEntity dvdEntity, Model model) {
		if (dvdEntity.getDvdname().equals("")) {
			// 給予初始化狀態
			List<String> emptyList = new ArrayList<>();
			model.addAttribute("ls", emptyList);

			model.addAttribute("additionalTextForAdding", "insert DVD name is blank");

			return "dvdkanri";

		} else if (dvdEntity.getDvdname().contains(" ")) {
			// 給予初始化狀態
			List<String> emptyList = new ArrayList<>();
			model.addAttribute("ls", emptyList);

			model.addAttribute("additionalTextForAdding", "insert DVD name contains blank");

			return "dvdkanri";

		} else {

			dvdEntity.setIns_by("Jim");

			dVDService.insertDVD(dvdEntity);

			// 給予初始化狀態
			List<String> emptyList = new ArrayList<>();
			model.addAttribute("ls", emptyList);

			model.addAttribute("additionalTextForAdding", "adding DVD successfully");

			return "dvdkanri";

		}
	}

	@RequestMapping(value = "/checkdeleteCondition", method = RequestMethod.GET)
	public String checkdeleteCondition(Model model) {

		List<DVDEntity> ls = dVDService.findByName("");

		Boolean isBorrowableExists = false;
		int cntOfLs;
		if (ls == null) {
			cntOfLs = 0;
		} else {
			cntOfLs = ls.size();
		}

		if (cntOfLs == 0) {
			// 沒分頁用
			model.addAttribute("ls", ls);

			model.addAttribute("additionalTextForAdding", "DVD list is blank. Cannot delete anyone.");

			return "dvdkanri";
		} else {
			// 判斷有沒有可借DVD

			for (DVDEntity entity : ls) {
				String status = entity.getStatus();

				// 如果需要判断状态
				if ("0".equals(status)) {
					isBorrowableExists = true;
					break;
				}
			}

			if (isBorrowableExists) {
				return "dvdDelete";
			} else {

				// 沒分頁用
				model.addAttribute("ls", ls);

				model.addAttribute("additionalTextForAdding", "Borrowable DVD is not exist. Cannot delete.");

				return "dvdkanri";

			}

		}

	}

	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(@RequestParam(value = "dvdId") int dvdId, Model model) {

		List<DVDEntity> ls = dVDService.findByName("");

		int minId = Integer.MAX_VALUE;
		int maxId = Integer.MIN_VALUE;

		System.out.println("Range_max_initial : " + maxId);
		System.out.println("Range_min_initial : " + minId);

		for (DVDEntity entity : ls) {
			int entitydvdId = entity.getDvdId();
			if (entitydvdId < minId) {
				minId = entitydvdId;
			}
			if (entitydvdId > maxId) {
				maxId = entitydvdId;
			}
		}

		System.out.println("Range_max : " + maxId);
		System.out.println("Range_min : " + minId);

		boolean isInRange = dvdId >= minId && dvdId <= maxId;

		if (isInRange) {

			boolean DVDIDexists = ls.stream().anyMatch(entity -> entity.getDvdId() == dvdId);

			if (DVDIDexists) {

				List<DVDEntity> lsbyID = dVDService.findById(dvdId);

				String status = lsbyID.get(0).getStatus();

				if (status.equals("0")) {

					dVDService.deletebyId(dvdId);
					// 沒分頁用
					ls = dVDService.findByName("");
					model.addAttribute("ls", ls);

					model.addAttribute("additionalTextForAdding", "DVD delete successfully.");
					return "dvdkanri";

				} else {
					// 沒分頁用
					model.addAttribute("ls", ls);

					model.addAttribute("additionalTextForAdding",
							"This DVD is borrowed by another one. Cannot delete.");

					return "dvdkanri";

				}

			} else {
				// 沒分頁用
				model.addAttribute("ls", ls);

				model.addAttribute("additionalTextForAdding",
						"Input DVD number is not in any of database. Cannot delete.");

				return "dvdkanri";

			}

		} else {
			// 沒分頁用
			model.addAttribute("ls", ls);

			model.addAttribute("additionalTextForAdding",
					"Input DVD number is not in range of current database. Cannot delete.");

			return "dvdkanri";

		}

	}

	@RequestMapping(value = "/borrow", method = RequestMethod.GET)
	public String borrowDVDbyID(@RequestParam(value = "dvdId") int dvdId, Model model) {

		List<DVDEntity> lsbyID = dVDService.findById(dvdId);
		List<DVDEntity> ls = dVDService.findByName("");

		if (lsbyID.get(0).getStatus().equals("1")) {

			model.addAttribute("ls", ls);

			model.addAttribute("additionalTextForAdding", "This DVD has been borrowed. Please choose another one.");
			return "dvdkanri";

		} else {
			ls = dVDService.findById(dvdId);

			model.addAttribute("dvdentity", ls.get(0));

			model.addAttribute("additionalTextForAdding", "This DVD is borrowed by you.");
			return "dvdborrow";

		}

	}

	@RequestMapping(value = "/borrow2", method = RequestMethod.POST)
	public String borrowentity(@ModelAttribute DVDEntity dvdEntity, Model model) {

		dvdEntity.setBorrowedCount(dvdEntity.getBorrowedCount() + 1);

		dVDService.borrowById(dvdEntity);

		// 沒分頁用
		List<DVDEntity> ls = dVDService.findByName("");
		model.addAttribute("ls", ls);

		model.addAttribute("additionalTextForAdding", "貸し出し手続き完了");

		return "dvdkanri";

	}

	@RequestMapping(value = "/back", method = RequestMethod.GET)
	public String backDVD(@RequestParam(value = "dvdId") int dvdId, Model model) {

		List<DVDEntity> lsbyID = dVDService.findById(dvdId);

		if (lsbyID.get(0).getStatus().equals("0")) {
			List<DVDEntity> ls = dVDService.findByName("");

			model.addAttribute("ls", ls);

			model.addAttribute("additionalTextForAdding", "此DVD已經在店內,無須歸還");
			return "dvdkanri";

		} else {
			dVDService.backById(dvdId);

			LocalDate borrowedDate = lsbyID.get(0).getBorrowedDate();
			LocalDate today = LocalDate.now();

// 使用Period类计算两个日期之间的时间段
			Period period = Period.between(borrowedDate, today);

// 获取天数差异
			int daysDifference = period.getDays() + 1;

			List<DVDEntity> ls = dVDService.findByName("");

			model.addAttribute("ls", ls);

			model.addAttribute("additionalTextForAdding", "總共花費" + daysDifference * 100 + "元");
			return "dvdkanri";
		}

	}
}
