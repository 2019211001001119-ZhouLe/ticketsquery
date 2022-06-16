package team.seven.ticketsquery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.seven.ticketsquery.domain.News;
import team.seven.ticketsquery.domain.ResultVO;
import team.seven.ticketsquery.enums.ResultStatusEnum;
import team.seven.ticketsquery.service.NewsService;

import java.util.List;

/**
 * User: 谢礼治
 * Date: 2022/6/16
 * Time: 9:31
 * Description: 新闻管理模块
 */

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    /**
     * 添加新闻
     * @return
     */
    @PostMapping("/add")
    public ResultVO saveNews(@RequestBody News news){
        newsService.save(news);
        return new ResultVO(ResultStatusEnum.SUCCESS);
    }

    //更新新闻
    @PostMapping("/update")
    public ResultVO updateNews(@RequestBody News news){
        newsService.updateById(news);
        return new ResultVO(ResultStatusEnum.SUCCESS);
    }

    //删除新闻
    @DeleteMapping("/delete/{newsId}")
    public ResultVO deleteNews(@PathVariable String newsId){
        newsService.removeById(newsId);
        return new ResultVO(ResultStatusEnum.SUCCESS);
    }

    //获取所有新闻
    @GetMapping("/all")
    public ResultVO getAllNews(){
        List<News> newsList = newsService.list();
        return new ResultVO(ResultStatusEnum.SUCCESS,newsList);
    }

}
