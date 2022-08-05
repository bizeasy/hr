import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInterviewGrade } from 'app/shared/model/interview-grade.model';

type EntityResponseType = HttpResponse<IInterviewGrade>;
type EntityArrayResponseType = HttpResponse<IInterviewGrade[]>;

@Injectable({ providedIn: 'root' })
export class InterviewGradeService {
  public resourceUrl = SERVER_API_URL + 'api/interview-grades';

  constructor(protected http: HttpClient) {}

  create(interviewGrade: IInterviewGrade): Observable<EntityResponseType> {
    return this.http.post<IInterviewGrade>(this.resourceUrl, interviewGrade, { observe: 'response' });
  }

  update(interviewGrade: IInterviewGrade): Observable<EntityResponseType> {
    return this.http.put<IInterviewGrade>(this.resourceUrl, interviewGrade, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInterviewGrade>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInterviewGrade[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
