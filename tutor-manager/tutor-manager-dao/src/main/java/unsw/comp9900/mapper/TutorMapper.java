package unsw.comp9900.mapper;

import org.apache.ibatis.annotations.Param;
import unsw.comp9900.pojo.Tutor;
import unsw.comp9900.pojo.TutorExample;
import unsw.comp9900.pojo.TutorWithBLOBs;

import java.util.List;

public interface TutorMapper {
    long countByExample(TutorExample example);

    int deleteByExample(TutorExample example);

    int deleteByPrimaryKey(Integer uid);

    int insert(TutorWithBLOBs row);

    int insertSelective(TutorWithBLOBs row);

    List<TutorWithBLOBs> selectByExampleWithBLOBs(TutorExample example);

    List<Tutor> selectByExample(TutorExample example);

    TutorWithBLOBs selectByPrimaryKey(Integer uid);

    int updateByExampleSelective(@Param("row") TutorWithBLOBs row, @Param("example") TutorExample example);

    int updateByExampleWithBLOBs(@Param("row") TutorWithBLOBs row, @Param("example") TutorExample example);

    int updateByExample(@Param("row") Tutor row, @Param("example") TutorExample example);

    int updateByPrimaryKeySelective(TutorWithBLOBs row);

    int updateByPrimaryKeyWithBLOBs(TutorWithBLOBs row);

    int updateByPrimaryKey(Tutor row);
}