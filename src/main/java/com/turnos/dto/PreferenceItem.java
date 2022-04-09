package com.turnos.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreferenceItem implements Serializable {
	private String id;
	private String name;
	private Integer quantity;
	private Float price;
}
