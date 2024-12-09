package com.example.samsung_delivery.service;


import com.example.samsung_delivery.entity.Point;
import com.example.samsung_delivery.entity.User;
import com.example.samsung_delivery.enums.PointStatus;
import com.example.samsung_delivery.error.errorcode.ErrorCode;
import com.example.samsung_delivery.error.exception.CustomException;
import com.example.samsung_delivery.repository.PointRepository;
import com.example.samsung_delivery.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class PointService {


    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    //point 적립
    public void savePoint(Long userId , Integer savePoint ){
        User findUser = userRepository.findById(userId).orElseThrow(
                ()->new CustomException(ErrorCode.USER_NOT_FOUND));

        // point 만료기한 설정
        LocalDateTime expiredAt = getExpriedLocalDateTime();

        Point point = new Point(savePoint , expiredAt);
        point.setUser(findUser);
        point.setStatus(PointStatus.AVAILABLE);
        pointRepository.save(point);
    }


    //point 사용
    public void usePoint(Long userId, Integer usePoint){
        User findUser = userRepository.findById(userId).orElseThrow(
                ()->new CustomException(ErrorCode.USER_NOT_FOUND));
        int userTotalPoint = getUserTotalPoint(userId);
        //해당유저가 사용할 point 이상 갖고있는지 판별
        if(userTotalPoint < usePoint){
            throw new CustomException(ErrorCode.POINT_NOT_ENOUGH);
        }
        Point point = new Point();
        point.setUser(findUser);
        point.usePoint(usePoint * -1);
        point.setStatus(PointStatus.AVAILABLE);
        pointRepository.save(point);
    }

    @Transactional
    public void statusUpdate(){
        pointRepository.updateStatus(PointStatus.UNAVAILABLE);
    }

    public int getUserTotalPoint(Long userId){
        User findUser = userRepository.findById(userId).orElseThrow(
                ()->new CustomException(ErrorCode.USER_NOT_FOUND));
        int countUser = pointRepository.countByUserId(userId);
        if (countUser != 0){
            return pointRepository.totalPoint(findUser.getId());
        }
       return 0;
    }


    // expiredAt 설정
    private static LocalDateTime getExpriedLocalDateTime() {
        LocalDate nowDate = LocalDate.now();
        LocalDate plusOneWeek = nowDate.plusWeeks(1);
        String plusOneWeekSt = plusOneWeek.toString();
        return LocalDateTime.parse(plusOneWeekSt+" 23:59:59",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
