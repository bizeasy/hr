import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJobInterviewType } from 'app/shared/model/job-interview-type.model';

type EntityResponseType = HttpResponse<IJobInterviewType>;
type EntityArrayResponseType = HttpResponse<IJobInterviewType[]>;

@Injectable({ providedIn: 'root' })
export class JobInterviewTypeService {
  public resourceUrl = SERVER_API_URL + 'api/job-interview-types';

  constructor(protected http: HttpClient) {}

  create(jobInterviewType: IJobInterviewType): Observable<EntityResponseType> {
    return this.http.post<IJobInterviewType>(this.resourceUrl, jobInterviewType, { observe: 'response' });
  }

  update(jobInterviewType: IJobInterviewType): Observable<EntityResponseType> {
    return this.http.put<IJobInterviewType>(this.resourceUrl, jobInterviewType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IJobInterviewType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IJobInterviewType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
