import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPerfReview } from 'app/shared/model/perf-review.model';

type EntityResponseType = HttpResponse<IPerfReview>;
type EntityArrayResponseType = HttpResponse<IPerfReview[]>;

@Injectable({ providedIn: 'root' })
export class PerfReviewService {
  public resourceUrl = SERVER_API_URL + 'api/perf-reviews';

  constructor(protected http: HttpClient) {}

  create(perfReview: IPerfReview): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(perfReview);
    return this.http
      .post<IPerfReview>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(perfReview: IPerfReview): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(perfReview);
    return this.http
      .put<IPerfReview>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPerfReview>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPerfReview[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(perfReview: IPerfReview): IPerfReview {
    const copy: IPerfReview = Object.assign({}, perfReview, {
      fromDate: perfReview.fromDate && perfReview.fromDate.isValid() ? perfReview.fromDate.format(DATE_FORMAT) : undefined,
      thruDate: perfReview.thruDate && perfReview.thruDate.isValid() ? perfReview.thruDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fromDate = res.body.fromDate ? moment(res.body.fromDate) : undefined;
      res.body.thruDate = res.body.thruDate ? moment(res.body.thruDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((perfReview: IPerfReview) => {
        perfReview.fromDate = perfReview.fromDate ? moment(perfReview.fromDate) : undefined;
        perfReview.thruDate = perfReview.thruDate ? moment(perfReview.thruDate) : undefined;
      });
    }
    return res;
  }
}
