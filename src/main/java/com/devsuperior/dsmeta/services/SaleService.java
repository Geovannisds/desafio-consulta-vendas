package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> getSalesReport(String name, String minDateStr, String maxDateStr, Pageable pageable) {

		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		LocalDate maxDate;
		if (maxDateStr != null && !maxDateStr.isEmpty()) {
			maxDate = LocalDate.parse(maxDateStr);
		} else {
			maxDate = today;
		}

		LocalDate minDate;
		if (minDateStr != null && !minDateStr.isEmpty()) {
			minDate = LocalDate.parse(minDateStr);
		} else {
			minDate = maxDate.minusYears(1L);
		}

		return repository.findSalesReport(name, minDate, maxDate, pageable);

	}

	public Page<SaleSummaryDTO> getSalesSummary(String minDateStr, String maxDateStr, Pageable pageable) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		LocalDate maxDate;
		if (maxDateStr != null && !maxDateStr.isEmpty()) {
			maxDate = LocalDate.parse(maxDateStr);
		} else {
			maxDate = today;
		}

		LocalDate minDate;
		if (minDateStr != null && !maxDateStr.isEmpty()) {
			minDate = LocalDate.parse(minDateStr);
		} else {
			minDate = maxDate.minusYears(1);
		}

		return repository.findSalesSammary(minDate, maxDate, pageable);
	}

}
