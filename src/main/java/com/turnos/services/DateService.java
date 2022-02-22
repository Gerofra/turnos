package com.turnos.services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class DateService {

	public Integer mesActual() {	
        int month;
        GregorianCalendar date = new GregorianCalendar();      
        month = date.get(Calendar.MONTH);
        month = month+1;
        return month;
	}
	
	public String nombreMesActual(int mes) {
		
		switch (mes) {
		case 1:
			return "Enero";
		case 2:
			return "Febrero";
		case 3:
			return "Marzo";
		case 4:
			return "Abril";		
		case 5:
			return "Mayo";
		case 6:
			return "Junio";
		case 7:
			return "Julio";
		case 8:
			return "Agosto";		
		case 9:
			return "Septiembre";
		case 10:
			return "Octubre";
		case 11:
			return "Noviembre";
		case 12:
			return "Diciembre";					
		default:
			break;
		}
		
		return null;		
	}
	
	public Integer anioActual() {
        int year;
        GregorianCalendar date = new GregorianCalendar();      
        year = date.get(Calendar.YEAR);
        return year;
	}

	public Integer diaActual() {
        int day;
        GregorianCalendar date = new GregorianCalendar();      
        day = date.get(Calendar.DAY_OF_MONTH);
        return day;
	}
	
	public Integer cantDias(Integer month, Integer year) {
	
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		int numDays = calendar.getActualMaximum(Calendar.DATE);
		
		return numDays;
	}
	
	public String fechaActual() {		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = format.format(new Date());

		
		return dateString;
	}
	
	public List<String> mesesDisponibles(){
		List<String> meses = new LinkedList<>();
        String[] months = {
                "",                               // leave empty so that we start with months[1] = "January"
                "Enero", "Febrero", "Marzo",
                "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre",
                "Octubre", "Noviembre", "Diciembre"
        };
        
        meses.add(months[mesActual()]);
        meses.add(months[mesActual()+1]);
        meses.add(months[mesActual()+2]);
        return meses;
	}
	
    public List<List<String>> calendario(int year, int month) {
    	
    	List<List<String>> calendario = new LinkedList<>();
    	List<String> semana = new ArrayList<>();

        YearMonth ym = YearMonth.of(year, month);

        int counter = 1;

        // Get day of week of 1st date of the month and print space for as many days as
        // distant from SUN
        int dayValue = LocalDate.of(year, month, 1).getDayOfWeek().getValue();

        
            if (dayValue != 7)
                for (int i = 0; i < dayValue; i++, counter++) {
                   // System.out.printf("%-4s", "");
                    semana.add(null);
                }


            for (int i = 1; i <= ym.getMonth().length(ym.isLeapYear()); i++, counter++) {
            	
               // System.out.printf("%-4d", i);
                semana.add(String.valueOf(i));
                
                // Break the line if the value of the counter is multiple of 7

                if (counter % 7 == 0) {
                	
                  //  System.out.println();
                    calendario.add(semana);
                    semana = new ArrayList<>();  
                }
            }
            calendario.add(semana);
            semana = new ArrayList<>(); 
          
        return calendario;
        
        
    }
       
    public List<List<List<String>>> calendarioAnual(){
    	
    	List<List<List<String>>> calendarioAnual = new LinkedList<List<List<String>>>();
    	
    	int anio = anioActual();
    	int mes = mesActual();
    	
    	for (int i = 0; i < 13; i++) {

			calendarioAnual.add(calendario(anio, mes));	
			
			if (mes == 12) {
				mes = 0;
				anio++;			
			}		
    		mes++;
		}	
		return calendarioAnual;   	
    }
    
    public List<Integer> listaMeses(){
    	
    	List<Integer> listaMeses = new LinkedList<Integer>();
    	int mes = mesActual();
    	
    	for (int i = 0; i < 13; i++) {
			listaMeses.add(mes);			
			if (mes == 12) {
				mes = 0;			
			}		
    		mes++;				
		}	   
    	return listaMeses;
    }
    
    public List<Integer> listaAnios(){
    	
    	List<Integer> listaAnios = new LinkedList<Integer>();
    	int anio = anioActual();
    	int mes = mesActual();   	
    	for (int i = 0; i < 13; i++) {
			listaAnios.add(anio);			
			if (mes == 12) {
				mes = 0;
				anio++;			
			}		
    		mes++;			
		}	   
    	return listaAnios;
    }
    
    public List<String> listaNombresMeses(){
    	
    	List<String> listaMeses = new LinkedList<String>();
    	int mes = mesActual();
    	
    	for (int i = 0; i < 13; i++) {
			listaMeses.add(nombreMesActual(mes));			
			if (mes == 12) {
				mes = 0;			
			}		
    		mes++;				
		}	   
    	return listaMeses;
    }
}
