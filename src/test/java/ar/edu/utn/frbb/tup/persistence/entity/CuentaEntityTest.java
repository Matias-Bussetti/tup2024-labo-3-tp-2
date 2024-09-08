package ar.edu.utn.frbb.tup.persistence.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaEntityTest {

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}
