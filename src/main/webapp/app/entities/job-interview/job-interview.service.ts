import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJobInterview } from 'app/shared/model/job-interview.model';

type EntityResponseType = HttpResponse<IJobInterview>;
type EntityArrayResponseType = HttpResponse<IJobInterview[]>;

@Injectable({ providedIn: 'root' })
export class JobInterviewService {
  public resourceUrl = SERVER_API_URL + 'api/job-interviews';

  constructor(protected http: HttpClient) {}

  create(jobInterview: IJobInterview): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobInterview);
    return this.http
      .post<IJobInterview>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jobInterview: IJobInterview): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobInterview);
    return this.http
      .put<IJobInterview>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJobInterview>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJobInterview[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(jobInterview: IJobInterview): IJobInterview {
    const copy: IJobInterview = Object.assign({}, jobInterview, {
      interviewDate: jobInterview.interviewDate && jobInterview.interviewDate.isValid() ? jobInterview.interviewDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.interviewDate = res.body.interviewDate ? moment(res.body.interviewDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((jobInterview: IJobInterview) => {
        jobInterview.interviewDate = jobInterview.interviewDate ? moment(jobInterview.interviewDate) : undefined;
      });
    }
    return res;
  }
}
