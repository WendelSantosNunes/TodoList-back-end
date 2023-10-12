package br.com.wendel.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

  // Aqui vamos mesclar o objeto original com novo objeto
  // Vamos substituir onde está null pelo valor do antigo objeto
  public static void copyNonNullProperties(Object source, Object target){
    // Cópia os valores de um objeto para outro objeto
    BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
  }

  // Essa função vai pegar todo os valores null
  public static String[] getNullPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);

    // Obter um array com todas as propriedade do objeto
    PropertyDescriptor[] pds = src.getPropertyDescriptors();

    // Criando conjunto com as proprieda com os valores null
    Set<String> emptyNames = new HashSet<>();

    // Agora, vamos adicionar as propriedade no array
    for( PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());

      if(srcValue == null) {
        emptyNames.add(pd.getName());  
      }
    }

    String[] result = new String[emptyNames.size()];

    return emptyNames.toArray(result);
  }
}
