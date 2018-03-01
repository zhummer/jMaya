import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { HostC } from './host-c.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<HostC>;

@Injectable()
export class HostCService {

    private resourceUrl =  SERVER_API_URL + 'api/hosts';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/hosts';

    constructor(private http: HttpClient) { }

    create(host: HostC): Observable<EntityResponseType> {
        const copy = this.convert(host);
        return this.http.post<HostC>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(host: HostC): Observable<EntityResponseType> {
        const copy = this.convert(host);
        return this.http.put<HostC>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<HostC>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<HostC[]>> {
        const options = createRequestOption(req);
        return this.http.get<HostC[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HostC[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<HostC[]>> {
        const options = createRequestOption(req);
        return this.http.get<HostC[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HostC[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: HostC = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<HostC[]>): HttpResponse<HostC[]> {
        const jsonResponse: HostC[] = res.body;
        const body: HostC[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to HostC.
     */
    private convertItemFromServer(host: HostC): HostC {
        const copy: HostC = Object.assign({}, host);
        return copy;
    }

    /**
     * Convert a HostC to a JSON which can be sent to the server.
     */
    private convert(host: HostC): HostC {
        const copy: HostC = Object.assign({}, host);
        return copy;
    }
}
