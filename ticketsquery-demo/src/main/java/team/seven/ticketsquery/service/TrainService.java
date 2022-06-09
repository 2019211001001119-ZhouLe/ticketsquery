package team.seven.ticketsquery.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import team.seven.ticketsquery.domain.Train;
import team.seven.ticketsquery.mapper.TrainMapper;

/**
 * @description: 列车Service
 * @author: ZhouLe
 * @create: 2022-06-09
 * @version: 1.0
 */
@Service
public class TrainService extends ServiceImpl<TrainMapper, Train> {
}
