package server.api.emailAuth.common.util;

import server.api.emailAuth.common.util.response.ResponseForm;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseUtil {

    /**
     *
     * @param contents
     * @return {
     *     "result" : true,
     *     "data" : {
     *         "contents" : contents
     *     }
     * }
     */
    public ResponseEntity<ResponseForm> makeResponseEntity(Object contents) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("contents", contents);
        ResponseForm responseForm = new ResponseForm(true, data);

        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }

    public ResponseEntity<ResponseForm> makeResponseEntity(Map<String, Object> data) {
        ResponseForm responseForm = new ResponseForm(true, data);

        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }

    public ResponseEntity<ResponseForm> makeResponseEntity(Map<String, Object> data, boolean result) {
        ResponseForm responseForm = new ResponseForm(result, data);

        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }

    /**
     *
     * @param contents
     * @return {
     *     "result" : true,
     *     "data" : {
     *         "contents" : contents,
     *         "pagination" : {
     *             "page" : page,
     *             "totalCount" : totalCount
     *         }
     *     }
     * }
     */

    public ResponseEntity<ResponseForm> makeResponseEntity(Page<?> page){

        return makeResponseEntity(page.getContent(), page.getNumber(), page.getTotalElements());
    }

    public ResponseEntity<ResponseForm> makeResponseEntity(Object contents, int page, Long totalCount){
        HashMap<String, Object> pagination = new HashMap<>();
        pagination.put("page", page + 1);
        pagination.put("totalCount", totalCount);

        HashMap<String, Object> data = new HashMap<>();
        data.put("contents", contents);
        data.put("pagination", pagination);

        ResponseForm responseForm = new ResponseForm(true, data);

        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }

    /**
     *
     * @return {
     *     "result" : true
     * }
     */
    public ResponseEntity<ResponseForm> makeResponseEntity(){
        ResponseForm responseForm = new ResponseForm(true);

        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }

    /**
     *
     * @param result
     * @return {
     *     "result" : result
     * }
     */
    public ResponseEntity<ResponseForm> makeResponseEntity(boolean result){
        ResponseForm responseForm = new ResponseForm(result);

        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }

    public ResponseEntity<ResponseForm> makeResponseEntity(Object contents, boolean result) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("contents", contents);
        ResponseForm responseForm = new ResponseForm(result, data);

        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }

}
