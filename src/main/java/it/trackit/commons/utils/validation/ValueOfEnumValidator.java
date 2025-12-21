package it.trackit.commons.utils.validation;

import it.trackit.commons.utils.ValueOfEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Stream;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, String> {
  List<String> acceptedValues;

  @Override
  public void initialize(ValueOfEnum constraintAnnotation) {
    acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
      .map(Enum::name)
      .toList();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true; // in questo modo sono le annotazioni @NotNull e @NotBlack
                   // che gestiscono i valori null passati
    };
    return acceptedValues.contains(value);
  }
}
