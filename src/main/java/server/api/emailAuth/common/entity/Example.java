package server.api.emailAuth.common.entity;

import server.api.emailAuth.common.entity.base.DateTimeBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "example")
@Getter @Setter
public class Example extends DateTimeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String content;

}
