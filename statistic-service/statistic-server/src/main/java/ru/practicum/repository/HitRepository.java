package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.entity.HitEntity;

import java.util.List;
import java.util.Optional;

public interface HitRepository extends JpaRepository<HitEntity, Long> {

    /*List<ViewStatsResponse> collectionStatistic(@Param("start") LocalDateTime start,
                                                @Param("end") LocalDateTime end,
                                                @Param("uris") List<String> uris);
*/
  //  @Query("SELECT e.app, e.uri, COUNT(e.ip) " +
    //i FROM Item AS i
//    @Query("SELECT e " +
//            "FROM endpointhit AS e ")// +
//    List<HitEntity> getListHitEntity();
           // "WHERE " +
           // "e.uri IN :uris " +
         //   "AND e.created BETWEEN :start and :end ")// +
           // "GROUP BY e.app, e.uri)") //+
          //  "ORDER BY DESC")
//    List<HitEntity> getListHitEntity(@Param("start") LocalDateTime start,
//                                     @Param("end") LocalDateTime end,
//                                     @Param("uris") List<String> uris);
Optional<HitEntity> findById(Long id);
    /*@Query("SELECT app, uri, count(*) \n" +
            "FROM endpointhit\n" +
            "WHERE \n" +
            "uri IN (:uris) \n" +
            "AND created BETWEEN :start and :end\n" +
            "group by app, uri")
    List<ViewStatsResponseDto> collectStatistic(LocalDateTime start, LocalDateTime end, List<String> uris);*/
    /*@Query("SELECT new ru.practicum.dto.ViewStatsResponseDto(e.app, e.uri, COUNT(e.ip)) " +
            "FROM endpointhit e " +
            "WHERE " +
            "e.uri IN :uris " +
            "AND e.created BETWEEN :start and :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(e.ip) DESC")*/
  /*  @SqlResultSetMapping(
            name = "countHitsMapping",
            classes = {
                    @ConstructorResult(
                            targetClass = ViewStatsResponseDto.class,
                            columns = {
                                    @ColumnResult(name="app"),
                                    @ColumnResult(name="uri"),
                                    @ColumnResult(name="hits")
                            }
                    )
            }
    )*/
//    @NamedNativeQuery(name="getHitsCount", query = "SELECT app, uri, count(*) \n" +
//            "FROM endpointhit\n" +
//            "WHERE \n" +
//            "uri IN (:uris) \n" +
//            "AND created BETWEEN :start and :end\n" +
//            "group by app, uri", resultSetMapping = "countHitsMapping")
//    List<ViewStatsResponseDto> countHits(@Param("start") LocalDateTime start,
//                                         @Param("end") LocalDateTime end,
//                                         @Param("uris") List<String> uris);

    //
//    List<ViewStatsResponse> result(LocalDateTime start, LocalDateTime end, List<String> uris);
//    @Query(value = "SELECT e.app, e.uri, COUNT(*)  \n" +
//            "FROM endpointhit e \n" +
//            "WHERE \n" +
//            "uri IN (?3) \n" +
//            "AND created BETWEEN ?1 and ?2 \n" +
//            "GROUP BY e.app, e.uri", nativeQuery = true)
//    List<ViewStatsDto> collectHitsStatistic(LocalDateTime start, LocalDateTime end, List<String> uris);
//    public static interface ViewStatsDto {
//        String getApp();
//        String getUri();
//        long getHits();
//    }
//    @Query(value = "SELECT e.app, e.uri, COUNT(*)  \n" +
//            "FROM endpointhit e \n" +
//            "WHERE \n" +
//            "created BETWEEN ?1 and ?2 \n" +
//            "GROUP BY e.app, e.uri", nativeQuery = true)
//    List<ViewStatsDto> collectHitsStatistic(LocalDateTime start, LocalDateTime end, List<String> uris);
//
//    @Query(value = "SELECT e.app, e.uri, COUNT(*)  \n" +
//            "FROM endpointhit e \n" +
//            "WHERE \n" +
//            "uri IN (?3) \n" +
//            "AND created BETWEEN ?1 and ?2 \n" +
//            "GROUP BY e.app, e.uri", nativeQuery = true)
//    List<StatsDto> collectStatistic(LocalDateTime start, LocalDateTime end, List<String> uris);
//
//    class StatsDto {
//        String app;
//        String uri;
//        long hits;
//    }
}