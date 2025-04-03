package github.com.LucasAlcoforad.TO_DO.dto.erro;

import java.util.List;

public record ResponseErro(int status, String mensage, List<FieldErro> body) {
}
