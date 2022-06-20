package team.seven.ticketsquery.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import team.seven.ticketsquery.domain.News;
import team.seven.ticketsquery.domain.NewsPage;
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
@CrossOrigin
@Transactional
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

    @GetMapping("/{newsId}")
    public ResultVO getById(@PathVariable Integer newsId){
        News news = newsService.getById(newsId);
        return new ResultVO(ResultStatusEnum.SUCCESS,news);
    }

    //条件分页查询
    @PostMapping("/queryByCondition")
    public ResultVO getNewsByCondition(@RequestBody NewsPage newsPage){
        Page<News> result = new Page<>(newsPage.getPageNum(), newsPage.getPageSize());
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(newsPage.getNewsName()!=null,"news_title",newsPage.getNewsName());
        queryWrapper.like(newsPage.getPublishDate()!=null,"news_publish_time",newsPage.getPublishDate());
        newsService.page(result,queryWrapper);
        return new ResultVO(ResultStatusEnum.SUCCESS,result);
    }

}
