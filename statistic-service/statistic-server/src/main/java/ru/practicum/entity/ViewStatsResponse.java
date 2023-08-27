package ru.practicum.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//@Entity
@Setter
@Getter
@NamedNativeQuery(name = "collectionStatistic", query = "SELECT e.app, e.uri, COUNT(*) \n" +
        "FROM endpointhit e \n" +
        "WHERE \n" +
        "uri IN :uris \n" +
        "AND created BETWEEN :start and :end \n" +
        "GROUP BY e.app, e.uri", resultSetMapping = "countHitsMapping")

@SqlResultSetMapping(
        name="countHitsMapping",
        classes={
                @ConstructorResult(
                        targetClass=ru.practicum.entity.ViewStatsResponse.class,
                        columns={
                                @ColumnResult(name="app", type = String.class),
                                @ColumnResult(name="uri", type = String.class),
                                @ColumnResult(name="hits", type = Long.class)

                        }
                )
        }
)

public class ViewStatsResponse {
    private String app;
    private String uri;
    private long hits;

  //  @Column(name = "app")
//    public String getApp() {
//        return app;
//    }

//    @Column(name = "uri")
//    public String getUri() {
//        return uri;
//    }

 //   @Column(name = "hits")
//    public long getHits() {
//        return hits;
//    }

}
