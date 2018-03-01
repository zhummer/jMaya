import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ForumC } from './forum-c.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ForumC>;

@Injectable()
export class ForumCService {

    private resourceUrl =  SERVER_API_URL + 'api/forums';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/forums';

    constructor(private http: HttpClient) { }

    create(forum: ForumC): Observable<EntityResponseType> {
        const copy = this.convert(forum);
        return this.http.post<ForumC>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(forum: ForumC): Observable<EntityResponseType> {
        const copy = this.convert(forum);
        return this.http.put<ForumC>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ForumC>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ForumC[]>> {
        const options = createRequestOption(req);
        return this.http.get<ForumC[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ForumC[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<ForumC[]>> {
        const options = createRequestOption(req);
        return this.http.get<ForumC[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ForumC[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ForumC = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ForumC[]>): HttpResponse<ForumC[]> {
        const jsonResponse: ForumC[] = res.body;
        const body: ForumC[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ForumC.
     */
    private convertItemFromServer(forum: ForumC): ForumC {
        const copy: ForumC = Object.assign({}, forum);
        return copy;
    }

    /**
     * Convert a ForumC to a JSON which can be sent to the server.
     */
    private convert(forum: ForumC): ForumC {
        const copy: ForumC = Object.assign({}, forum);
        return copy;
    }
}
